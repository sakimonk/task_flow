package com.tf.task.flow.api.model.converter;

import com.tf.task.flow.api.model.entity.User;
import com.tf.task.flow.api.model.view.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ouweijian
 * @date 2025/3/11 23:23
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserVO toView(User user);

}
