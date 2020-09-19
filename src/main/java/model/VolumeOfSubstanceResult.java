
package model;

public class VolumeOfSubstanceResult {
    private int volume;

    public VolumeOfSubstanceResult(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }


    @Override
    public String toString() {
        return String.valueOf(volume);
    }
}
