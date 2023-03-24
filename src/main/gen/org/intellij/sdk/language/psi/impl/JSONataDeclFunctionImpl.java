// This is a generated file. Not intended for manual editing.
package org.intellij.sdk.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.dochub.idea.arch.jsonata.psi.JSONataTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.intellij.sdk.language.psi.*;

public class JSONataDeclFunctionImpl extends ASTWrapperPsiElement implements JSONataDeclFunction {

  public JSONataDeclFunctionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JSONataVisitor visitor) {
    visitor.visitDeclFunction(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JSONataVisitor) accept((JSONataVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public JSONataFuncParams getFuncParams() {
    return findNotNullChildByClass(JSONataFuncParams.class);
  }

  @Override
  @NotNull
  public JSONataJsonata getJsonata() {
    return findNotNullChildByClass(JSONataJsonata.class);
  }

}
