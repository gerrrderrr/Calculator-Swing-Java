package calculator.desk.util;


import javax.swing.text.*;

public class AutoTextWrapper extends StyledEditorKit {

    @Override
    public ViewFactory getViewFactory() {
        return new WarpColumnFactory();
    }

    private static class WarpColumnFactory implements ViewFactory {

        @Override
        public View create(Element element) {
            String kind = element.getName();
            if (kind != null) {
                switch (kind) {
                    case AbstractDocument.ContentElementName:
                        return new WarpLabelView(element);
                    case AbstractDocument.ParagraphElementName:
                        return new ParagraphView(element);
                    case AbstractDocument.SectionElementName:
                        return new BoxView(element, View.Y_AXIS);
                    case StyleConstants.ComponentElementName:
                        return new ComponentView(element);
                    case StyleConstants.IconElementName:
                        return new IconView(element);
                }
            }
            return new LabelView(element);
        }
    }

    private static class WarpLabelView extends LabelView {
        public WarpLabelView(Element elem) {
            super(elem);
        }

        @Override
        public float getMinimumSpan(int axis) {
            switch (axis) {
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid Axis:" + axis);
            }
        }
    }
}