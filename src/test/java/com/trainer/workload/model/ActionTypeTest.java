package com.trainer.workload.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ActionTypeTest {

    @Test
    void testActionTypeValues() {
        ActionType addAction = ActionType.valueOf("ADD");
        ActionType deleteAction = ActionType.valueOf("DELETE");

        assertNotNull(addAction);
        assertNotNull(deleteAction);
        assertEquals(ActionType.ADD, addAction);
        assertEquals(ActionType.DELETE, deleteAction);
    }

    @Test
    void testActionTypeCount() {
        ActionType[] actionTypes = ActionType.values();
        assertEquals(2, actionTypes.length);
    }
}