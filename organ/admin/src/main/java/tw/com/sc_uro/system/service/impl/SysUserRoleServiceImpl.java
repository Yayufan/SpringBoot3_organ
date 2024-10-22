package tw.com.sc_uro.system.service.impl;

import tw.com.sc_uro.system.mapper.SysUserRoleMapper;
import tw.com.sc_uro.system.pojo.entity.SysUserRole;
import tw.com.sc_uro.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用戶與角色 - 多對多關聯表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
