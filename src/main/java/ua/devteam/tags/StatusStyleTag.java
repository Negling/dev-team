package ua.devteam.tags;

import ua.devteam.entity.enums.CheckStatus;
import ua.devteam.entity.enums.DeveloperStatus;
import ua.devteam.entity.enums.Status;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class StatusStyleTag extends SimpleTagSupport {

    private Enum status;

    public void setStatus(Enum status) {
        this.status = status;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String style;

        if (status instanceof Status) {
            style = evaluateStatus((Status) status);
        } else if (status instanceof CheckStatus) {
            style = evaluateCheckStatus((CheckStatus) status);
        } else {
            style = evaluateDeveloperStatus((DeveloperStatus) status);
        }

        getJspContext().getOut().write(style);
    }

    private String evaluateStatus(Status status) {
        switch (status) {
            case Pending:
                return "color: blue !important;";// blue
            case Declined:
                return "color: #DC143C !important;";// red
            case Canceled:
                return "color: purple !important;";// purple
            case Running:
                return "color: darkcyan !important;";// cyan
            case Complete:
                return "color: #449d44 !important;";// lime
            default:
                return "";
        }
    }

    private String evaluateCheckStatus(CheckStatus status) {
        switch (status) {
            case Awaiting:
                return "color: blue !important;";
            case Declined:
                return "color: #DC143C !important;";
            case Paid:
                return "color: #449d44 !important;";
            default:
                return "";
        }
    }

    private String evaluateDeveloperStatus(DeveloperStatus status) {
        switch (status) {
            case Available:
                return "color: #449d44 !important;";
            case Holiday:
                return "color: darkcyan !important;";
            case Locked:
                return "color: #DC143C !important;";
            case Hired:
                return "color: blue !important;";
            default:
                return "";
        }
    }
}
