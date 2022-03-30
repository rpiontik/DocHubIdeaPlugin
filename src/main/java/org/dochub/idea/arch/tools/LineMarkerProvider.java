package org.dochub.idea.arch.tools;

import com.intellij.codeInsight.daemon.*;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.util.messages.Topic;
import org.dochub.idea.arch.references.providers.RefComponentID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.impl.YAMLPlainTextImpl;

import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class LineMarkerProvider extends LineMarkerProviderDescriptor {

    public static Topic<NavigateMessage> ON_NAVIGATE_MESSAGE = Topic.create("Navigate to", NavigateMessage.class);

    public interface NavigateMessage {
        void go(String entity, String id);
    }

    private class DocHubNavigationHandler implements GutterIconNavigationHandler {
        private String entity = null;
        private String id = null;
        public DocHubNavigationHandler(String entity, String id) {
            super();
            this.entity = entity;
            this.id = id;
        }
        @Override
        public void navigate(MouseEvent e, com.intellij.psi.PsiElement elt) {
            NavigateMessage publisher = elt.getProject().getMessageBus().syncPublisher(ON_NAVIGATE_MESSAGE);
            publisher.go(entity, id);
        }
    }

    @Override
    public final void collectSlowLineMarkers(@NotNull List<? extends PsiElement> elements, @NotNull Collection<? super LineMarkerInfo<?>> result) {
        for (int i = 0, size = elements.size(); i < size; i++) {
            PsiElement element = elements.get(i);
            collectNavigationMarkers(element, result);
        }
    }

    protected void collectNavigationMarkers(
            @NotNull PsiElement element,
            @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result
    ) {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        LineMarkerInfo result = null;
        if (RefComponentID.pattern().accepts(element)) {
            String id = null;
            PsiElement markElement = element;
            if (element instanceof YAMLKeyValue) {
                markElement = element.getFirstChild();
                id = ((YAMLKeyValue)element).getName();
            } else if (element instanceof YAMLPlainTextImpl) {
                markElement = element.getFirstChild();
                id = element.getText();
            }
            if (id != null) {
                DocHubNavigationHandler naviHandler = new DocHubNavigationHandler("component", id);
                result = new LineMarkerInfo<>(
                        markElement,
                        markElement.getTextRange(),
                        AllIcons.Actions.Preview,
                        new Function<PsiElement, String>() {
                            @Override
                            public String fun(PsiElement element) {
                                return "Показать в DocHub";
                            }
                        },
                        naviHandler,
                        GutterIconRenderer.Alignment.LEFT,
                        new Supplier<String>() {
                            @Override
                            public String get() {
                                return "DocHub";
                            }
                        }
                );
            }
        }
        return result;
    }
}