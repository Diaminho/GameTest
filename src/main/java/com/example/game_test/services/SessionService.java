package com.example.game_test.services;

import com.example.game_test.enteties.Session;
import com.example.game_test.enteties.User;
import com.example.game_test.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    UserService userService;

    public Session findSessionById(Long id){
        return sessionRepository.findSessionById(id);
    }

    public Session findSessionByUserId(Long userId){
        return sessionRepository.findSessionByUserId(userId);
    }

    public Session createSessionForUserId(Long userId){
        Session session=new Session(userId);
        sessionRepository.save(session);
        return session;
    }

    public void deleteSession(Long id){
        sessionRepository.delete(sessionRepository.findSessionById(id));
    }

    public User findUserBySessionId(Long sessionId){
        return userService.findById(findSessionById(sessionId).getUserId());
    }
}
