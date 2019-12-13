package com.example.game_test.services;

import com.example.game_test.entity.Session;
import com.example.game_test.entity.User;
import com.example.game_test.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserService userService;

    public SessionRepository getSessionRepository() {
        return sessionRepository;
    }

    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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

    public Session findById(Long id){
        return sessionRepository.findById(id).orElse(null);
    }
}