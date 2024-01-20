package org.project.chats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.chats.dto.common.CommonResponseDto;
import org.project.chats.dto.request.ChatRoomRequestDto;
import org.project.chats.service.ChatRoomService;
import org.project.chats.service.CommonService;
import org.project.chats.type.SuccessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final CommonService commonService;

    @PostMapping("/chat/room")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createChatRoom(@RequestHeader("Authorization") String accessToken,
                                                 @RequestBody ChatRoomRequestDto chatRoomRequestDto) {

        log.info("채팅방 생성 API 호출");
        String roomId = chatRoomService.createChatRoom(accessToken, chatRoomRequestDto);
        CommonResponseDto<Object> commonResponseDto =
                commonService.successResponse(SuccessMessage.CHAT_ROOM_CREATE_SUCCESS.getDescription(),
                        roomId);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseDto);
    }

    @GetMapping("/chat/room")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> selectChatRoomName() {
        log.info("채팅방 조회 API 호출");
        List<String> chatRoomNames = chatRoomService.chatRoomNameList();
        CommonResponseDto<Object> commonResponseDto =
                commonService.successResponse(SuccessMessage.CHAT_ROOM_SELECT_SUCCESS.getDescription(),
                        chatRoomNames);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponseDto);
    }

    @GetMapping("/chat/{roomName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> selectChatRoomId(@PathVariable String roomName) {
        log.info("채팅방 아이디 반환 API 호출");
        String roomId = chatRoomService.chatRoomId(roomName);
        CommonResponseDto<Object> commonResponseDto =
                commonService.successResponse(SuccessMessage.CHAT_ROOM_ID_SELECT_SUCCESS.getDescription(),
                       roomId);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponseDto);
    }
}
