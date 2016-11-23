package befaster.runner;

public enum RunnerAction {
    getNewRoundDescription(false),
    connectivityTest(false),
    processRealRequests(true);

    private boolean shouldPublish;

    RunnerAction(boolean shouldPublish) {
        this.shouldPublish = shouldPublish;
    }

    public boolean isShouldPublish() {
        return shouldPublish;
    }
}
