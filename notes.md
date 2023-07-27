### Lifecycle
```plain
Is scene running?
[No]
1. Create all GameObjects -> for each:
   * Add all components
2. Start all GameObjects -> for each component:
   * Start component
3. Run scene

[Yes] (If you need to dynamically add GameObject to scene while its running)
1. Create GameObject
   * Add all components
2. Add GameObject to scene
   * Start GameObject
3. Start all GameObjects -> for each component:
   * Start component
```