package com.project.BE_banjjokee.alarm;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 사용자에게 SSE(Server Side Event) 방식으로 데이터를 전송하기 위한 클래스
 * 다양한 스레드에서 SseEmiter 객체를 저장하는 store 에 접근하기 때문에 ConcurrentHashMap 을 사용하였습니다
 * 알림을 허용한 회원들에 한해 add 메서드를 통해 store 객체에 저장하여 응답받을 곳에 대한 정보를 저장하고
 * 알림이 발생할 이벤트가 발생하는 곳에서 Alarms#push 를 호출하여 알람을 받을 회원들에게 서버측에서 데이터를 전송할 수 있도록 할 수 있습니다.
 */
@Component
public class Alarms {

    private final Map<UUID, SseEmitter> store = new ConcurrentHashMap<>();

    /**
     * SSE 에 대한 데이터를 받을 클라이언트를 저장하는 store 에 사용자를 등록하는 메서드
     * @param uuid 사용자의 uuid
     * @param sseEmitter 해당 사용자의 응답을 위한 SseEmitter 객체
     */
    public void add(UUID uuid, SseEmitter sseEmitter) {
        this.store.put(uuid, sseEmitter);
        sseEmitter.onCompletion(() -> {
            this.store.remove(uuid);
        });
        sseEmitter.onTimeout(sseEmitter::complete);
    }

    /**
     * 알림이 필요한 이벤트 발생시 알림을 받을 사용자 uuid 를 이용하여 응답데이터를 전송할 수 있도록 하는 메서드
     * 게시글이나 댓글에 대한 댓글이 발생시 바로 이동할 수 있도록 id 를 데이터로 전송
     * @param uuids 알림 데이터를 전송할 사용자 uuid
     * @param id 댓글이 발생한 게시글 id, 스케쥴 정보가 담긴 스케쥴 id 등 알림이 필요한 데이터에 대한 id
     */
    public void push(Set<UUID> uuids, Long id) {
        try {
            for (UUID uuid : uuids) {
                if (this.store.containsKey(uuid)) {
                    this.store.get(uuid).send(SseEmitter.event()
                            .name("alarm")
                            .data(id));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("알람 전송 실패");
        }
    }
}
