package com.computer.socialcampus.service;

public abstract class NotificationSetting {

    private String settingName;
    private boolean enabled;

    public NotificationSetting(String settingName) {
        this.settingName = settingName;
        this.enabled = false; // Default to disabled
    }

    public String getSettingName() {
        return settingName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract void saveSetting(String userId);

    public abstract void loadSetting(String userId);
}
