package org.ediver.ascuba.preferences.migrations;

import android.content.SharedPreferences;

public interface Migration {
	void migrate(SharedPreferences prefs);
}
