package tw.com.sc_uro.system.service.impl;

import tw.com.sc_uro.system.mapper.SysRoleMenuMapper;
import tw.com.sc_uro.system.pojo.entity.SysRoleMenu;
import tw.com.sc_uro.system.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色與菜單 - 多對多關聯表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

}
