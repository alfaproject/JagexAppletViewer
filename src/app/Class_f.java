package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class Class_f implements ActionListener {
  public final void actionPerformed(ActionEvent paramActionEvent) {
    if (appletviewer.sub_260c(true) >= 0)
      DialogFactory.ShowOk(LanguageStrings.Get("changes_on_restart"));
  }
}

/* Location:           C:\Windows\.jagex_cache_32\jagexlauncher\bin\jagexappletviewer\
 * Qualified Name:     app.Class_f
 * JD-Core Version:    0.5.4
 */