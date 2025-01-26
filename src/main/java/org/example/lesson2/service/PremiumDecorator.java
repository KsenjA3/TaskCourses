package org.example.lesson2.service;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class PremiumDecorator implements Handler{
    protected Handler subgroupHandler;
}
