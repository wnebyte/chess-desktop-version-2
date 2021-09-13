package media;

import model.state.StateDescription;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class AudioManager
{
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final String move_sound
            = AudioManager.class.getResource("/audio/chess_6.wav").getPath();

    private static final String capture_sound
            = AudioManager.class.getResource("/audio/chess_capture.wav").getPath();

    private static final String check_sound =
            AudioManager.class.getResource("/audio/chess_check.wav").getPath();

    public static void playChessAudio(List<StateDescription> stateDescriptions)
    {
        if ((stateDescriptions == null) || stateDescriptions.isEmpty()) {
            return;
        }

        Runnable runnable;

        if (stateDescriptions.contains(StateDescription.CHECK)) {
            runnable = new Audio(check_sound);
        }
        else if (stateDescriptions.contains(StateDescription.CAPTURE)) {
            runnable = new Audio(capture_sound);
        }
        else {
            runnable = new Audio(move_sound);
        }

        executorService.submit(runnable);
    }

    public static void play(Audio audio)
    {
        executorService.submit(audio);
    }
}
