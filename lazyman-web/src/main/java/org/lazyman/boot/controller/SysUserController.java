package org.lazyman.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.sys.dto.*;
import org.lazyman.boot.sys.service.ISysUserService;
import org.lazyman.boot.sys.vo.SysUserDetailVO;
import org.lazyman.boot.sys.vo.SysUserVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.util.ThreadLocalUtils;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.starter.oss.OssFile;
import org.lazyman.starter.oss.OssTemplate;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统用户管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-02-26
 */
@Api(tags = "系统用户管理接口")
@RestController
@RequestMapping("/api/sysusers")
public class SysUserController extends BaseController {
    @Resource
    private ISysUserService iSysUserService;
    @Resource
    private OssTemplate ossTemplate;

    @ApiOperation(value = "新增系统用户")
    @PostMapping
    @Idempotency
    public ResultVO<Long> saveSysUser(@Valid @RequestBody SysUserFormDTO sysUserFormDTO) {
        return ResultVO.ok(iSysUserService.save(sysUserFormDTO));
    }

    @ApiOperation(value = "编辑系统用户")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editSysUser(@ApiParam(value = "系统用户ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody SysUserFormDTO sysUserFormDTO) {
        sysUserFormDTO.setId(id);
        return ResultVO.ok(iSysUserService.edit(sysUserFormDTO));
    }

    @ApiOperation(value = "删除系统用户")
    @DeleteMapping(value = "/{ids}")
    public ResultVO deleteSysUser(@ApiParam(value = "系统用户ID集合，多个用英文逗号隔开", required = true) @PathVariable(value = "ids") Long[] ids) {
        return ResultVO.ok(iSysUserService.delete(ids));
    }

    @ApiOperation(value = "根据ID查询系统用户详情")
    @GetMapping(value = "/{id}")
    public ResultVO<SysUserDetailVO> getSysUserDetail(@ApiParam(value = "系统用户ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iSysUserService.getDetail(id));
    }

    @ApiOperation(value = "分页查询系统用户")
    @GetMapping
    public ResultVO<PageVO<SysUserVO>> listByPage(SysUserQueryDTO sysUserQueryDTO) {
        return ResultVO.ok(iSysUserService.listByPage(sysUserQueryDTO));
    }

    @ApiOperation(value = "查询系统用户下拉选项")
    @GetMapping(value = "/options")
    public ResultVO<List<SysUserVO>> listSelectOptions(@ApiParam(value = "搜索关键字") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultVO.ok(iSysUserService.listSelectOptions(keyword));
    }

    @ApiOperation(value = "启用/禁用系统用户")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "系统用户ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iSysUserService.updateState(stateActionDTO));
    }

    @ApiOperation(value = "系统用户重置密码")
    @PatchMapping(value = "/resetPwd/{id}")
    @Idempotency
    public ResultVO resetSysUserPwd(@ApiParam(value = "系统用户ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody SysUserResetPwdDTO sysUserResetPwdDTO) {
        sysUserResetPwdDTO.setId(id);
        return ResultVO.ok(iSysUserService.resetSysUserPwd(sysUserResetPwdDTO));
    }

    @ApiOperation(value = "用户中心查询profile")
    @GetMapping(value = "/uc/profile")
    public ResultVO<SysUserDetailVO> getSysUserProfile() {
        return ResultVO.ok(iSysUserService.getDetail(ThreadLocalUtils.getCurrentUserId()));
    }

    @ApiOperation(value = "用户中心编辑profile")
    @PatchMapping(value = "/uc/profile")
    @Idempotency
    public ResultVO updateSysUserProfile(@Valid @RequestBody SysUserProfileFormDTO sysUserProfileFormDTO) {
        sysUserProfileFormDTO.setId(ThreadLocalUtils.getCurrentUserId());
        return ResultVO.ok(iSysUserService.updateSysUserProfile(sysUserProfileFormDTO));
    }

    @ApiOperation(value = "用户中心修改密码")
    @PatchMapping(value = "/uc/updatePwd")
    @Idempotency
    public ResultVO updateSysUserPwd(@Valid @RequestBody SysUserUpdatePwdDTO sysUserUpdatePwdDTO) {
        sysUserUpdatePwdDTO.setId(ThreadLocalUtils.getCurrentUserId());
        return ResultVO.ok(iSysUserService.updateSysUserPwd(sysUserUpdatePwdDTO));
    }

    @ApiOperation(value = "文件上传")
    @PostMapping("/uc/uploadAvatar")
    public ResultVO<OssFile> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.getSize() == 0) {
            return ResultVO.error(CommonErrCode.BAD_REQUEST);
        }
        try {
            OssFile ossFile = ossTemplate.putFile(file);
            SysUserProfileFormDTO sysUserProfileFormDTO = new SysUserProfileFormDTO();
            sysUserProfileFormDTO.setId(ThreadLocalUtils.getCurrentUserId());
            sysUserProfileFormDTO.setAvatar(ossFile.getLink());
            iSysUserService.updateSysUserProfile(sysUserProfileFormDTO);
            return ResultVO.ok(ossFile);
        } catch (Exception e) {
            return ResultVO.error(CommonErrCode.INTERNAL_SERVER_ERROR);
        }
    }
}
