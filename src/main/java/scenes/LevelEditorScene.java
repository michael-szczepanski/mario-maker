package scenes;

import components.GridLines;
import components.MouseControls;
import components.Sprite;
import components.Spritesheet;
import imgui.ImGui;
import imgui.ImVec2;
import jade.Camera;
import jade.GameObject;
import jade.Prefabs;
import jade.Transform;
import org.joml.Vector2f;
import org.joml.Vector3f;
import physics2d.PhysicsSystem2D;
import physics2d.primitives.Circle;
import physics2d.rigidbody.Rigidbody2D;
import renderer.DebugDraw;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private Spritesheet sprites;

    GameObject levelEditorComponents = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);
    PhysicsSystem2D physics = new PhysicsSystem2D(1.0f / 60.0f, new Vector2f(0, -10));
    Transform obj1, obj2, obj3;
    Rigidbody2D rb1, rb2, rb3;

    @Override
    public void init() {
        levelEditorComponents.addComponent(new MouseControls());
        levelEditorComponents.addComponent(new GridLines());

        obj1 = new Transform(new Vector2f(500, 600));
        obj2 = new Transform(new Vector2f(490, 500));
        obj3 = new Transform(new Vector2f(480, 400));

        rb1 = new Rigidbody2D();
        rb2 = new Rigidbody2D();
        rb3 = new Rigidbody2D();
        rb1.setRawTransform(obj1);
        rb2.setRawTransform(obj2);
        rb3.setRawTransform(obj3);
        rb1.setMass(100);
        rb2.setMass(200);
        rb3.setMass(300);


        Circle c1 = new Circle();
        c1.setRadius(10.0f);
        c1.setRigidbody(rb1);
        rb1.setCollider(c1);
        Circle c2 = new Circle();
        c2.setRadius(20.0f);
        c2.setRigidbody(rb2);
        rb2.setCollider(c2);
        Circle c3 = new Circle();
        c3.setRadius(30.0f);
        c3.setRigidbody(rb3);
        rb3.setCollider(c3);

        physics.addRigidbody(rb1, true);
        physics.addRigidbody(rb2, true);
        physics.addRigidbody(rb3, false);

        loadResources();

        this.camera = new Camera(new Vector2f(0, 0));
        sprites = AssetPool.getSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png");

        if (levelLoaded) {
            if (gameObjects.size() > 0) {
                this.activeGameObject = gameObjects.get(0);
            }
            return;
        }
    }


    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"), 16, 16, 81, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");
    }

    float t = 0.0f;
    @Override
    public void update(float dt) {
        levelEditorComponents.update(dt);

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        DebugDraw.addCircle(obj1.position, 10.0f, new Vector3f(1, 0, 0));
        DebugDraw.addCircle(obj2.position, 20.0f, new Vector3f(0, 0, 1));
        DebugDraw.addCircle(obj3.position, 30.0f, new Vector3f(0, 1, 0));
        physics.update(dt);

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x; // The rightmost coordinate of the displayed window
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            // Set the width and height of the sprites we would like to be displayed as
            float spriteWidth = sprite.getWidth() * 2.0f;
            float spriteHeight = sprite.getHeight() * 2.0f;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i); // Gives all sprites a unique ID, since ImGui uses texture id for button clicks

            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
                // Attach this object to the mouse cursor
                levelEditorComponents.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
