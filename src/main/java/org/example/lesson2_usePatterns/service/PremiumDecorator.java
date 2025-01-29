package org.example.lesson2_usePatterns.service;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class PremiumDecorator implements Handler{
    protected Handler subgroupHandler;
}
