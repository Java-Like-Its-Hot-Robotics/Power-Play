package org.firstinspires.ftc.teamcode.api.event;

import java.util.concurrent.Callable;

//this could be refactored into more classes with visitor pattern,
//but that is a lot of work and is terribly ugly
public interface RobotEventI<T extends Enum<T>> {
    static enum NullEvent implements RobotEventI<NullEvent> {NullEvent;

        @Override
        public RobotEventI.NullEvent isNull() {
            return NullEvent;
        }

        @Override
        public RobotEventI.NullEvent getValue() {
            return NullEvent;
        }

        @Override
        public RobotEventI.NullEvent getInit() {
            return NullEvent;
        }

        @Override
        public RobotEventI.NullEvent getStart() {
            return NullEvent;
        }

        @Override
        public RobotEventI.NullEvent getStop() {
            return NullEvent;
        }
    }

    T isNull();
    T getValue();
    T getInit();
    T getStart();
    T getStop();
}
