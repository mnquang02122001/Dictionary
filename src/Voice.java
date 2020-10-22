import com.sun.speech.freetts.VoiceManager;

public class Voice {
    private final com.sun.speech.freetts.Voice voice;

    public Voice(String name) {
        this.voice = VoiceManager.getInstance().getVoice(name);
        this.voice.allocate();
    }

    public void say(String st) {
        this.voice.speak(st);
        this.voice.deallocate();
    }
}
