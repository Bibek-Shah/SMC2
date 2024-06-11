package com.contact.smc.Helper;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {
    private String content;
    @Builder.Default
    private MessageType type = MessageType.BLUE;
}