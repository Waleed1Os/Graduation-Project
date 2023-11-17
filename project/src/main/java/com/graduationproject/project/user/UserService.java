package com.graduationproject.project.user;







import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserService {
private final UserRepository userRepository;
public UserTDO getUser(int id){
    User user=userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
    ModelMapper mapper=new ModelMapper();
    return mapper.map(user, UserTDO.class);
}

}

