package org.firstinspires.ftc.teamcode.api.sensor;

/**
 * An interface for anything that can be positioned.
 * Examples include motors, servos, lifts, etc.
 * It is parameterized over the required type.
 */
public interface IPositionable<T> {
    T getPosition();
    void setPosition(T position);
    void setVelocity(double velocity);
    boolean isAt(T position);

    T getTargetPosition();
}
