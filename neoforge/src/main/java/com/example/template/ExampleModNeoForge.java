package com.example.template;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("template")
public class ExampleModNeoForge {
    public ExampleModNeoForge(IEventBus bus) {
        ExampleMod.init();
        System.out.println("Hello from neoforge!");
    }
}