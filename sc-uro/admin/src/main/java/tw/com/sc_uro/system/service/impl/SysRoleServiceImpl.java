package tw.com.sc_uro.system.service.impl;

import tw.com.sc_uro.system.mapper.SysRoleMapper;
import tw.com.sc_uro.system.pojo.entity.SysRole;
import tw.com.sc_uro.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 - 透過設置角色達成較廣泛的權限管理 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
