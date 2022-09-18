package meu.primeiro.devprototipo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.GnssStatus;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GNSSView extends View {
    private GnssStatus newStatus;
    private int r;
    private int height, width;
    private static int gnssType;


    public GNSSView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void onSatelliteStatusChanged(GnssStatus status) {
        newStatus = status;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // coletando informações do tamanho tela de desenho
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        // definindo o raio da esfera celeste
        if (width < height)
            r = (int) (width / 2 * 0.9);
        else
            r = (int) (height / 2 * 0.9);

        // configurando o pincel para desenhar a projeção da esfera celeste
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);

        Paint circlePain = new Paint();
        circlePain.setStyle(Paint.Style.FILL);
        circlePain.setColor(Color.argb(255, 255, 0, 0));

        Paint painSats = new Paint();
        painSats.setStyle(Paint.Style.FILL);
        painSats.setColor(Color.WHITE);

        // Desenha a projeção da esfera celeste
        // desenhando círculos concêntricos
        int radius = r;
        canvas.drawCircle(computeXc(0), computeYc(0), radius, paint);
        radius = (int) (radius * Math.cos(Math.toRadians(45)));
        canvas.drawCircle(computeXc(0), computeYc(0), radius, paint);
        radius = (int) (radius * Math.cos(Math.toRadians(60)));
        canvas.drawCircle(computeXc(0), computeYc(0), radius, paint);

        //desenhando os eixos
        canvas.drawLine(computeXc(0), computeYc(-r), computeXc(0), computeYc(r), paint);
        canvas.drawLine(computeXc(-r), computeYc(0), computeXc(r), computeYc(0), paint);

        // configura o pincel para desenhar os satélites
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        if (newStatus != null) {
            for (int i = 0; i < newStatus.getSatelliteCount(); i++) {
                switch (gnssType) {
                    case 0:
                        break;
                    case GnssStatus.CONSTELLATION_GPS:
                        if (newStatus.getConstellationType(i) != GnssStatus.CONSTELLATION_GPS)
                            continue;
                        break;
                    case GnssStatus.CONSTELLATION_BEIDOU:
                        if (newStatus.getConstellationType(i) != GnssStatus.CONSTELLATION_BEIDOU)
                            continue;
                        break;
                    case GnssStatus.CONSTELLATION_GLONASS:
                        if (newStatus.getConstellationType(i) != GnssStatus.CONSTELLATION_GLONASS)
                            continue;
                        break;
                    case GnssStatus.CONSTELLATION_GALILEO:
                        if (newStatus.getConstellationType(i) != GnssStatus.CONSTELLATION_GALILEO)
                            continue;
                        break;
                }

                float az = newStatus.getAzimuthDegrees(i);
                float el = newStatus.getElevationDegrees(i);
                float x = (float) (r * Math.cos(Math.toRadians(el)) * Math.sin(Math.toRadians(az)));
                float y = (float) (r * Math.cos(Math.toRadians(el)) * Math.cos(Math.toRadians(az)));

                painSats.setTextAlign(Paint.Align.LEFT);
                painSats.setTextSize(30);

                circlePain.setTextAlign(Paint.Align.CENTER);
                circlePain.setTextSize(30);

                circlePain = setCircleCollor(circlePain, newStatus.getConstellationType(i));
                canvas.drawCircle(computeXc(x) - 7, computeYc(y), 20, circlePain);
                String satID = newStatus.getSvid(i) + "";
                canvas.drawText(satID, computeXc(x) + 10, computeYc(y) + 10, painSats);

            }
        }
    }

    private int computeXc(double x) {
        return (int) (x + width / 2);
    }

    private int computeYc(double y) {
        return (int) (-y + height / 2);
    }

    private Paint setCircleCollor(Paint circle, int id) {

        switch (id) {
            case GnssStatus.CONSTELLATION_GPS:
                circle.setColor(Color.YELLOW);
                break;
            case GnssStatus.CONSTELLATION_BEIDOU:
                circle.setColor(Color.RED);
                break;
            case GnssStatus.CONSTELLATION_GLONASS:
                circle.setColor(Color.MAGENTA);
                break;
            case GnssStatus.CONSTELLATION_GALILEO:
                circle.setColor(Color.GRAY);
                break;
        }

        return circle;
    }

}
