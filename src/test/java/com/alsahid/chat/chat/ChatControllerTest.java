package com.alsahid.chat.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatControllerTest {

    @Mock
    private SimpMessageHeaderAccessor headerAccessor;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() {
        // Given
        ChatMessage chatMessage = new ChatMessage("Hello, world!", "John", MessageType.CHAT);

        // When
        ChatMessage result = chatController.sendMessage(chatMessage);

        // Then
        assertEquals(chatMessage, result);
    }


    @Test
    public void testAddUser() {
        // Given
        ChatMessage chatMessage = new ChatMessage("Joined the chat.", "John", MessageType.JOIN);

        // Create a SimpMessageHeaderAccessor and set the username attribute
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("username", chatMessage.getSender());
        headerAccessor.setSessionAttributes(sessionAttributes);

        // When
        ChatMessage result = chatController.addUser(chatMessage, headerAccessor);

        // Then
        assertEquals(chatMessage, result);
        assertEquals("John", headerAccessor.getSessionAttributes().get("username"));
    }

}
