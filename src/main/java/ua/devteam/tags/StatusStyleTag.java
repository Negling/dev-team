package ua.devteam.tags;

import lombok.Setter;
import ua.devteam.entity.enums.CheckStatus;
import ua.devteam.entity.enums.DeveloperStatus;
import ua.devteam.entity.enums.Status;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Simple tag that returns appropriate color code that corresponds to ingoing status value.
 * <p>
 * Supports next "status" enums:
 * <p><ul>
 * <li> Status enum
 * <li> CheckStatus enum
 * <li> DeveloperStatus enum
 * </ul>
 * <p>
 * Returns next color values:
 * <p><ul>
 * <li> blue - blue
 * <li> #DC143C - red
 * <li> purple - purple
 * <li> darkcyan - cyan
 * <li> #449d44 - lime
 * <li> empty string - in case of default values
 * </ul>
 * <p>
 */
public class StatusStyleTag extends SimpleTagSupport {

    private @Setter Enum status;

    @Override
    public void doTag() throws JspException, IOException {
        String style;

        if (status instanceof Status) {
            style = evaluateStatus((Status) status);
        } else if (status instanceof CheckStatus) {
            style = evaluateCheckStatus((CheckStatus) status);
        } else if (status instanceof DeveloperStatus) {
            style = evaluateDeveloperStatus((DeveloperStatus) status);
        } else {
            throw new SkipPageException();
        }

        getJspContext().getOut().write(style);
    }

    /**
     * Returns appropriate color to ingoing Status value
     *
     * @param status current status
     * @return string with colour code
     */
    private String evaluateStatus(Status status) {
        switch (status) {
            case PENDING:
                return "color: blue !important;";// blue
            case DECLINED:
                return "color: #DC143C !important;";// red
            case CANCELED:
                return "color: purple !important;";// purple
            case RUNNING:
                return "color: darkcyan !important;";// cyan
            case COMPLETE:
                return "color: #449d44 !important;";// lime
            default:
                return "";
        }
    }

    /**
     * Returns appropriate color to ingoing CheckStatus value
     *
     * @param status current status
     * @return string with color code
     */
    private String evaluateCheckStatus(CheckStatus status) {
        switch (status) {
            case AWAITING:
                return "color: blue !important;";
            case DECLINED:
                return "color: #DC143C !important;";
            case PAID:
                return "color: #449d44 !important;";
            default:
                return "";
        }
    }

    /**
     * Returns appropriate color to ingoing DeveloperStatus value
     *
     * @param status current status
     * @return string with color code
     */
    private String evaluateDeveloperStatus(DeveloperStatus status) {
        switch (status) {
            case AVAILABLE:
                return "color: #449d44 !important;";
            case HOLIDAY:
                return "color: darkcyan !important;";
            case LOCKED:
                return "color: #DC143C !important;";
            case HIRED:
                return "color: blue !important;";
            default:
                return "";
        }
    }
}
