package lib3;

public class StblBox {

        private int stcooffset;

        private int stscoffset;

        private int stszoffset;

        private int stssoffset;

        private int stsdoffset;

    public void setStssoffset(int stssoffset) {
        this.stssoffset = stssoffset;
    }

    public int getStssoffset() {
        return stssoffset;
    }

    public void setStsdoffset(int stsdoffset) {
        this.stsdoffset = stsdoffset;
    }

    public int getStsdoffset() {
        return stsdoffset;
    }

    public StblBox(int stcooffset, int stscoffset, int stszoffset, int stssoffset, int stsdoffset) {
        this.stcooffset = stcooffset;
        this.stscoffset = stscoffset;
        this.stszoffset = stszoffset;
        this.stssoffset = stssoffset;
        this.stsdoffset = stsdoffset;
    }

        public int getStcooffset() {

            return stcooffset;

        }

        public void setStcooffset(int stcooffset) {

            this.stcooffset = stcooffset;

        }

        public int getStscoffset() {

            return stscoffset;

        }

        public void setStscoffset(int stscoffset) {

            this.stscoffset = stscoffset;

        }

        public int getStszoffset() {
        return stszoffset;
    }

        public void setStszoffset(int stszoffset) {
        this.stszoffset = stszoffset;
    }
}
