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
                return "blue";
            case Declined:
                return "red";
            case Canceled:
                return "purple";
            case Running:
                return "cyan";
            case Complete:
                return "lime";
            default:
                return "";
        }
    }

    private String evaluateCheckStatus(CheckStatus status) {
        switch (status) {
            case Awaiting:
                return "blue";
            case Declined:
                return "red";
            case Paid:
                return "lime";
            default:
                return "";
        }
    }

    private String evaluateDeveloperStatus(DeveloperStatus status) {
        switch (status) {
            case Available:
                return "lime";
            case Holiday:
                return "cyan";
            case Locked:
                return "red";
            case Hired:
                return "blue";
            default:
                return "";
        }
    }
}
