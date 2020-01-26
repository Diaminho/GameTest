package com.example.game_test.service;

import com.example.game_test.entity.Player;
import com.example.game_test.entity.Session;
import com.example.game_test.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final PlayerService playerService;

    public SessionService(SessionRepository sessionRepository, PlayerService playerService) {
        this.sessionRepository = sessionRepository;
        this.playerService = playerService;
    }

    public Session findSessionById(Long id){
        return sessionRepository.findSessionById(id);
    }

    public Session findSessionByUserId(Long userId){
        return sessionRepository.findSessionByUserId(userId);
    }

    public Session createSessionForUserId(Long userId) {
        Session session = new Session(userId);
        sessionRepository.save(session);
        return session;
    }

    public void deleteSession(Long id) {
        sessionRepository.delete(sessionRepository.findSessionById(id));
    }

    public Player findUserBySessionId(Long sessionId) {
        return playerService.findById(sessionRepository.findSessionById(sessionId).getUserId());
    }

    public Session findById(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }
}