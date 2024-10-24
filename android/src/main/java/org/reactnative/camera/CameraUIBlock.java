package org.reactnative.camera;

import com.facebook.react.bridge.UIManager;
import com.facebook.react.fabric.FabricUIManager;
import com.facebook.react.fabric.interop.UIBlockViewResolver;
import com.facebook.react.uimanager.common.UIManagerType;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.UIManagerModule;

import java.util.function.Function;

interface UIBlockInterface extends UIBlock, com.facebook.react.fabric.interop.UIBlock {
}

public class CameraUIBlock implements UIBlockInterface {
  private int tag;
  private Function<RNCameraView, Void> operation;

  public CameraUIBlock(int tag, Function<RNCameraView, Void> operation) {
    this.tag = tag;
    this.operation = operation;
  }

  @Override
  public void execute(NativeViewHierarchyManager nvhm) {
    executeImpl(nvhm, null);
  }

  @Override
  public void execute(UIBlockViewResolver uiBlockViewResolver) {
    executeImpl(null, uiBlockViewResolver);
  }

  private void executeImpl(NativeViewHierarchyManager nvhm, UIBlockViewResolver uiBlockViewResolver) {
    RNCameraView view = uiBlockViewResolver != null ? (RNCameraView) uiBlockViewResolver.resolveView(tag)
        : (RNCameraView) nvhm.resolveView(tag);

    if (view == null) {
      throw new IllegalStateException("RNCameraView with tag " + tag + " not found");
    }

    operation.apply(view);
  }
}
