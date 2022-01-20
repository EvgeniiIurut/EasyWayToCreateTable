package com.evgeniiurut.anotation;

@Table(title = "dogs")
public class Dog {
    @Column
    int id;

    @Column
    String name;

    @Column
    String color;

    @Column
    int weight;

    public Dog(int id, String name, String color, int weight) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
    }
}
