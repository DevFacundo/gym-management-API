package com.example.gym_management.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ClassScheduleTest {
    @Test
    void tryToPutCorrectMaxValueOfCapacity(){
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setMaxCapacity(5);
        assertEquals(5, classSchedule.getMaxCapacity());
    }
}
