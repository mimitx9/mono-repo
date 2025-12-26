package com.example.projector.projection.example;

import com.example.domain.example.event.UserCreatedEvent;
import com.example.projector.projection.ProjectionHandler;
import org.springframework.stereotype.Component;

/**
 * Ví dụ Projection Handler - UserProjectionHandler.
 * Handler này update user read model khi có UserCreatedEvent.
 */
@Component
public class UserProjectionHandler extends ProjectionHandler<UserCreatedEvent> {

    @Override
    protected void project(UserCreatedEvent event) {
        // Update read model/view
        // Ví dụ: tạo user summary, update user list view, etc.
        logger.info("Updating user projection: UserId={}, Name={}, Email={}", 
            event.getUserId(), event.getUserName(), event.getUserEmail());
        
        // Có thể lưu vào read database, update cache, etc.
        // projectionRepository.save(new UserView(...));
    }

    @Override
    public Class<UserCreatedEvent> getEventType() {
        return UserCreatedEvent.class;
    }
}

