package sh.ricky.core.util.ext;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import sh.ricky.core.constant.GlobalConstants;
import sh.ricky.core.util.DateUtil;

public class DateEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            this.setValue(GlobalConstants.FORM_DATE_NULL);
        } else {
            this.setValue(DateUtil.string2Date(text, null));
        }
    }
}
