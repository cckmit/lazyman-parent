package org.lazyman.boot.controller;

import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lazyman.boot.base.controller.BaseController;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.vo.PageVO;
import org.lazyman.boot.base.vo.ResultVO;
import org.lazyman.boot.listener.excel.AppUserUploadListener;
import org.lazyman.boot.user.dto.AppUserFormDTO;
import org.lazyman.boot.user.dto.AppUserQueryDTO;
import org.lazyman.boot.user.service.IAppUserService;
import org.lazyman.boot.user.vo.AppUserVO;
import org.lazyman.starter.redisson.annotation.Idempotency;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * <p>
 * APP用户管理接口 前端控制器
 * </p>
 *
 * @author wanglong
 * @since 2021-03-06
 */
@Api(tags = "APP用户管理接口")
@RestController
@RequestMapping("/api/appUsers")
public class AppUserController extends BaseController {
    @Resource
    private IAppUserService iAppUserService;

    @ApiOperation(value = "编辑APP用户")
    @PutMapping(value = "/{id}")
    @Idempotency
    public ResultVO editWishUser(@ApiParam(value = "APP用户ID", required = true) @PathVariable(value = "id") Long id, @Valid @RequestBody AppUserFormDTO appUserFormDTO) {
        appUserFormDTO.setId(id);
        return ResultVO.ok(iAppUserService.edit(appUserFormDTO));
    }

    @ApiOperation(value = "根据ID查询APP用户详情")
    @GetMapping(value = "/{id}")
    public ResultVO<AppUserVO> getWishUserDetail(@ApiParam(value = "APP用户ID", required = true) @PathVariable(value = "id") Long id) {
        return ResultVO.ok(iAppUserService.getDetail(id));
    }

    @ApiOperation(value = "分页查询APP用户")
    @GetMapping
    public ResultVO<PageVO<AppUserVO>> listByPage(AppUserQueryDTO appUserQueryDTO) {
        return ResultVO.ok(iAppUserService.listByPage(appUserQueryDTO));
    }

    @ApiOperation(value = "启用/禁用APP用户")
    @PatchMapping(value = "/{id}")
    public ResultVO updateState(@ApiParam(value = "APP用户ID", required = true) @PathVariable("id") Long id, @Valid @RequestBody StateActionDTO stateActionDTO) {
        stateActionDTO.setId(id);
        return ResultVO.ok(iAppUserService.updateState(stateActionDTO));
    }

    @ApiOperation(value = "导入APP用户")
    @PostMapping(value = "/upload")
    public ResultVO upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), AppUserVO.class, new AppUserUploadListener(iAppUserService)).sheet().doRead();
        return ResultVO.ok();
    }

    @ApiOperation(value = "导出APP用户")
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), AppUserVO.class).sheet("模板").doWrite(iAppUserService.listByExport(new AppUserQueryDTO()));
    }
}
