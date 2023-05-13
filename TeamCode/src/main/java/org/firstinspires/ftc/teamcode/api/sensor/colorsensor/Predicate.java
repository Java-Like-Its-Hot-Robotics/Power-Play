package org.firstinspires.ftc.teamcode.api.sensor.colorsensor;

@FunctionalInterface
public interface Predicate<I> {
    boolean test(I input);
}
