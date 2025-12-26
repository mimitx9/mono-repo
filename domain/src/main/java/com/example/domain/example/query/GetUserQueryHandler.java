package com.example.domain.example.query;

import com.example.domain.example.User;
import com.example.domain.query.QueryHandler;
import com.example.domain.spi.Repository;

/**
 * Ví dụ Query Handler - GetUserQueryHandler.
 * Handler này xử lý GetUserQuery và trả về thông tin user.
 * 
 * Note: @Component annotation sẽ được thêm bởi executable modules (api, mngt-api, etc.)
 * thông qua @ComponentScan hoặc manual bean registration.
 */
public class GetUserQueryHandler implements QueryHandler<GetUserQuery, GetUserQueryResult> {
    
    private final Repository<User, String> userRepository;

    public GetUserQueryHandler(Repository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public GetUserQueryResult handle(GetUserQuery query) {
        User user = userRepository.findById(query.userId());
        
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + query.userId());
        }
        
        return new GetUserQueryResult(
            user.getId(),
            user.getName(),
            user.getEmailValue()
        );
    }
}

