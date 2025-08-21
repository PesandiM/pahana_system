package util;

import jakarta.servlet.http.HttpSession;
import model.BillItem;

import java.util.ArrayList;
import java.util.List;

public class SessionHelper {
    private static final String CURRENT_BILL = "currentBill";

    @SuppressWarnings("unchecked")
    public static List<BillItem> getCurrentBill(HttpSession session) {
        Object obj = session.getAttribute(CURRENT_BILL);
        if (obj == null) {
            List<BillItem> newBill = new ArrayList<>();
            session.setAttribute(CURRENT_BILL, newBill);
            return newBill;
        }
        return (List<BillItem>) obj;
    }

    public static void addItemToBill(HttpSession session, BillItem item) {
        List<BillItem> currentBill = getCurrentBill(session);
        currentBill.add(item);
        session.setAttribute(CURRENT_BILL, currentBill);
    }

    public static void clearBill(HttpSession session) {
        session.removeAttribute(CURRENT_BILL);
    }
}
