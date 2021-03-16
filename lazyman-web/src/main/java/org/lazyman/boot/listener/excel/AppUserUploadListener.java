package org.lazyman.boot.listener.excel;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.lazyman.boot.user.service.IAppUserService;
import org.lazyman.boot.user.vo.AppUserVO;

@NoArgsConstructor
@AllArgsConstructor
public class AppUserUploadListener extends AnalysisEventListener<AppUserVO> {

    private IAppUserService appUserService;

    @Override
    public void invoke(AppUserVO appUserVO, AnalysisContext analysisContext) {
        System.out.println(JSONUtil.toJsonStr(appUserVO));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
