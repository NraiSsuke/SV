import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.analysis.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ShootING_VoiceING extends PApplet {



Minim minim;
AudioInput in;
FFT fft;

PFont font_text;
PFont font_time;
PFont font_pm;
PFont font_title;

int start_frame = 0;
int title_count;
boolean mouse_release_start = false;

float start_sound_x;
float start_sound_y;

float explosion_point_x;
float explosion_point_y;

float voiceing_x = -600;
float shooting_y = -50;

boolean go_title = false;

int block_num_def = 9; // ブロックの数

boolean game_start = false;

// 球
int ball_size = 10; // 球のサイズ

// 背景
int back_H = 200, back_S = 80, back_B = 20;// 背景の色
int back_line_H = 185, back_line_S = 80, back_line_B = 30; // 背景の線の色

// ブロック
int block_num;
int block_fi_H = 310, block_fi_S = 60, block_fi_B = 100, block_fi_A; // ブロックの色
int block_st_H = 170, block_st_S = 80, block_st_B = 100; // ブロックの枠の色
float block_st_thick = 1.5f; // ブロックの枠の太さ
int[] left_upper_x; // 左上のx座標
int[] left_upper_y; // 左上のy座標
int[] right_under_x; // 右下のx座標
int[] right_under_y; // 右下のy座標

// 照準
int aim_H = 200;
int aim_S = 80;
int aim_B = 50; //撃つ照準の線の色
int aim_thick = 3; // 撃つ照準の線の太さ

// soundBall
int sound_fi_H = 170;
int sound_fi_S = 95;
int sound_fi_B = 90;// soundBallの色
float sound_ajust = 0.3f; // soundBallの速さ調整 
soundBall soundBall;

// shootBall
int ball_num = 5; // shootBallの数
int ball_num_def = ball_num; // shootBallの数を保存
int shoot_fi_H = 310;
int shoot_fi_S = 60;
int shoot_fi_B = 100; // shootBallの色
int ball_count = 0; // shootBallを撃った回数
float[] x_v; // shootBallの方向調整x
float[] y_v; // shootBallの方向調整y
int[] x_d; // shootBallの向き
int[] y_d; // shootBallの向き
float shoot_angle; // 角度
float inter;
float ajust;
shootBall[] shootBall = new shootBall[ball_num];

boolean mouse_release = false; // マウスを離したら

int effect_frame = 24; // ワープエフェクトを続けるフレーム数
float effect_size = 2.5f; // ワープエフェクトのサイズ
int effect_thick = 5; // ワープエフェクトの枠の太さ
int effect_fi_S = 20;
int effect_fi_B = 60;
int effect_st_S = 50;
int effect_st_B = 100;// ワープエフェクトの色
int frame_count;
int warp_out_effect_count = 0;
int warp_in_effect_count = effect_frame;
int effect_fi_H = 0;
int effect_st_H = 360;
int in_effect_A = 255;
int out_effect_A = 255; 
float warp_x;
float warp_y;
int ball_now;
float[] sound_on_x;
float[] sound_on_y;
float[][] shoot_on_x;
float[][] shoot_on_y;
boolean delete = false;

int sound_fi_c;
int shoot_fi_c;

boolean up_right_x;
boolean up_right_y;
boolean under_right_x;
boolean under_right_y;
boolean under_left_x;
boolean under_left_y;
boolean up_left_x;
boolean up_left_y;

boolean game_over = false;
boolean explosion = false;
float[] explosion_x;
float[] explosion_y;
float[] stop_x;
float[] stop_y;
float[] explosion_dx;
float[] explosion_dy;
int explosion_count;
int explosion_A = 255;
int explosion_num = 50; // 爆発したときの球の数

boolean game_continue = false;
int continue_count = 0;
int button_st_H = 310;
int button_st_S = 40;
int button_st_B = 80; // continueボタンの枠の色
int button_thick = 3; // continueボタンの枠の太さ
int game_over_H = 310;
int game_over_S = 100;
int game_over_B = 80;
int game_over_A = 0; // GAME OVERの文字の色
int lets_go_H = 170;
int lets_go_S = 60;
int lets_go_B = 100; // Let's Go!の文字の色
int button_fi_H = 170;
int button_fi_S = 100;
int button_fi_B = 60; //continueボタンの色
int push_fi_H = 170;
int push_fi_S = 100;
int push_fi_B = 80; // continueボタンを押した後のボタン色
int continue_H = 310;
int continue_S = 0;
int continue_B = 80; // continueの文字の色
int push_continue_H = 310;
int push_continue_S = 0;
int push_continue_B = 100; // continueボタンを押した後の文字の色

int s;
int m;
int h;
String text_h = "00";
String text_m = "00"; 
String text_s = "00";
String text_f = "00";

int time_H = 175;
int time_S = 90;
int time_B = 95;

int time_x;
int time_y;

int game_over_y = 300;

int blight;

int continue_button_x;
int continue_button_y;
int continue_text_x = 194;
int continue_text_y = 473;


public void setup(){
  // インスタンス化
  left_upper_x = new int[block_num_def];
  left_upper_y = new int[block_num_def];
  right_under_x = new int[block_num_def];
  right_under_y = new int[block_num_def];
  
  // ブロックの座標設定
  left_upper_x[0] = 40;  right_under_x[0] = 300;
  left_upper_y[0] = 40;  right_under_y[0] = 120;
  left_upper_x[1] = 80;  right_under_x[1] = 520;
  left_upper_y[1] = 460;  right_under_y[1] = 610;
  left_upper_x[2] = 20;  right_under_x[2] = 120;
  left_upper_y[2] = 160;  right_under_y[2] = 240;
  left_upper_x[3] = 240;  right_under_x[3] = 360;
  left_upper_y[3] = 460;  right_under_y[3] = 480;
  left_upper_x[4] = 80;  right_under_x[4] = 100;
  left_upper_y[4] = 520;  right_under_y[4] = 600;
  left_upper_x[5] = 500;  right_under_x[5] = 520;
  left_upper_y[5] = 520;  right_under_y[5] = 600;
  left_upper_x[6] = 200;  right_under_x[6] = 380;
  left_upper_y[6] = 240;  right_under_y[6] = 300;
  left_upper_x[7] = 500;  right_under_x[7] = 560;
  left_upper_y[7] = 40;  right_under_y[7] = 100;
  left_upper_x[8] = 80;  right_under_x[8] = 140;
  left_upper_y[8] = 320;  right_under_y[8] = 380;
  
  sound_on_x = new float[60];
  sound_on_y = new float[60];
  explosion_x = new float[explosion_num];
  explosion_y = new float[explosion_num];
  explosion_dx = new float[explosion_num];
  explosion_dy = new float[explosion_num];
  font_text = loadFont("NirmalaUI-Semilight-107.vlw");
  font_time = loadFont("Rockwell-40.vlw");
  font_pm = loadFont("MS-UIGothic-70.vlw");
  font_title = loadFont("YuGothic-Bold-100.vlw");
  
  
  
  frameRate(60);
  
  colorMode(HSB, 360, 100, 100);
  
  // 背景を描写
  BackGround(0);
  
  // soundBallの設定
  minim = new Minim(this);
  in = minim.getLineIn();
  fft = new FFT(in.bufferSize(), in.sampleRate());
  
  sound_fi_c = color(sound_fi_H, sound_fi_S, sound_fi_B);
  soundBall = new soundBall(random(1, 600), 0, sound_fi_c);
}

public void draw(){
  colorMode(HSB, 360, 100, 100);
  rectMode(CORNERS);
  sound_fi_c = color(sound_fi_H, sound_fi_S, sound_fi_B);
  
  if(!game_start){
    block_num = 0;
    BackGround(0);
    if(start_frame == 0){
      if(voiceing_x <= 80){
        textFont(font_title);
        fill(shoot_fi_H, shoot_fi_S, shoot_fi_B);
        text("ShootING",67, shooting_y);
        fill(sound_fi_H, sound_fi_S, sound_fi_B);
        text("VoiceING", voiceing_x, 227);
        if(voiceing_x <= 80) voiceing_x += 4;
        if(voiceing_x >= 64) shooting_y += 25;
      }
      else{
        background(back_H, back_S, back_B);
        SoundBall();
        for(int i = 0; i < 30; i++){
          stroke(back_line_H, back_line_S, back_line_B);
          strokeWeight(1);
          line(0, i * 20, width, i * 20);
          line(i * 20, 0, i * 20, height);
        }
        textFont(font_title);
        fill(shoot_fi_H, shoot_fi_S, shoot_fi_B);
        text("ShootING",67, 150);
        fill(sound_fi_H, sound_fi_S, sound_fi_B);
        text("VoiceING", 80, 227);

        // + / - のボタンを表示
        textSize(24);
        fill(shoot_fi_H, shoot_fi_S, shoot_fi_B);
        text("shoot ball's", 235, 318);
        strokeWeight(4);
        fill(330, 50, 70, 60);
        stroke(330, 50, 70);
        rect(162, 322, 238, 398);
        fill(270, 50, 70, 60);
        stroke(270, 50, 70);
        rect(362, 322, 438, 398);
        textFont(font_pm);
        fill(330, 50, 70, 180);
        text("+", 183.5f, 385);
        fill(270, 50, 70, 180);
        text("-", 383.5f, 385);
    
        boolean plus_x = (mouseX >= 162 && mouseX <= 238);
        boolean plus_y = (mouseY >= 322 && mouseY <= 398);
        if(plus_x && plus_y){
          fill(330, 50, 70, 60);
          stroke(330, 50, 70);
          rect(162, 322, 238, 398);
          fill(360, 100, 100);
          text("+", 183.5f, 385);
          if(mouse_release_start){
            ball_num++;
            mouse_release_start= false;
          }
        }
        
        boolean minus_x = (mouseX >= 362 && mouseX <= 438);
        boolean minus_y = (mouseY >= 322 && mouseY <= 398);
        if(minus_x && minus_y){
          if(ball_num > 1){
            fill(270, 50, 70, 60);
            stroke(270, 50, 70);
            rect(362, 322, 438, 398);
            fill(230, 100, 100);
            text("-", 383.5f, 385);
            if(mouse_release_start){
              ball_num--;
              mouse_release_start= false;
            }
          }
          else mouse_release_start = false;
        }
        else mouse_release_start = false;
        
        // shootBallの数を表示
        fill(0, 0, 100, 50);
        stroke(shoot_fi_H, shoot_fi_S, shoot_fi_B);
        strokeWeight(4);
        rect(242, 322, 358, 398);
        fill(shoot_fi_H, shoot_fi_S, shoot_fi_B);
        textFont(font_time);
        textSize(50);
        if(ball_num < 10) text(str(ball_num), 287, 376);
        else if(ball_num < 100)text(str(ball_num), 274, 376);
        else text(str(ball_num), 261, 376);
       
        // スタートボタンを表示
        fill(170, 95, 90, 90);
        stroke(170, 95, 90);
        strokeWeight(4);
        rect(100, 440, 500, 540);
        fill(170, 35, 90);
        textFont(font_title);
        textSize(45);
        text("Game Start", 176, 507);
        boolean start_x = (mouseX >= 100 && mouseX <= 500);
        boolean start_y = (mouseY >= 440 && mouseY <= 540);
        if(start_x && start_y){
          fill(170, 95, 90, 90);
          stroke(170, 95, 90);
          strokeWeight(4);
          rect(100, 440, 500, 540);
          textFont(font_title);
          textSize(45);
          text("Game Start", 176, 507);
          if(mousePressed){
            start_sound_x = soundBall.pos.x;
            start_sound_y = soundBall.pos.y;
            start_frame = frameCount;
         }
       }
     }
    }
    // start_frameにframeCount が入ったら2秒立つまで
    else{
      if(start_frame + 60 < frameCount && frameCount%60 == 0){ 
        ball_num_def = ball_num;
        x_v = new float[ball_num_def];
        y_v = new float[ball_num_def];
        x_d = new int[ball_num_def];
        y_d = new int[ball_num_def];
        stop_x = new float[ball_num_def];
        stop_y = new float[ball_num_def];
        shoot_on_x = new float[ball_num_def][60];
        shoot_on_y = new float[ball_num_def][60];
        sound_fi_c = color(sound_fi_H, sound_fi_S, sound_fi_B);
        soundBall = new soundBall(random(1, 600), 0, sound_fi_c);
        shootBall = new shootBall[ball_num];
        ball_num = 0;
        block_num = block_num_def;
        game_start = true;
      } 
      else{
        background(back_H, back_S, back_B);
        fill(sound_fi_H, sound_fi_S, sound_fi_B);
        ellipse(start_sound_x, start_sound_y, ball_size, ball_size);
        for(int i = 0; i < 30; i++){
          stroke(back_line_H, back_line_S, back_line_B);
          strokeWeight(1);
          line(0, i * 20, width, i * 20);
          line(i * 20, 0, i * 20, height);
        }
        start_sound_y -= 30;
        
        block_num = 1;
        Block();
        TextTime();
      
        // Let's Go! 表示
        fill(lets_go_H, lets_go_S, lets_go_B);
        textFont(font_text);
        text("Let's GO!", 100, 285); 
        
        fill(170, 95, 90, 180);
        stroke(170, 95, 90);
        strokeWeight(4);
        rect(100, 440, 500, 540);
        fill(170, 50, 100);
        textFont(font_title);
        textSize(45);
        text("Enjoy the Game!", 120, 507);
      } 
    }
    return;
  }
  
  if(!game_over){
    
    // 背景を描写
    BackGround(0);
  
    // ブロックを描写
    Block();
    
    // 時間の計算・表示
    if(frameCount % 60 == 0){
      if(s % 59 == 0 && s != 0){
        if(s == 59) s = 0;
          if(m % 59 == 0 && m != 0){
            m = 0;
            h++;
          }
        else m++;
      }
      else s++;
    }
    boolean h1 = h < 10;
    boolean m1 = m < 10;
    boolean s1 = s < 10;
    boolean f1 = frameCount%60 < 10;
    if(h1) text_h = ("0" + str(h));
    else text_h = str(h);
    if(m1) text_m = ("0" + str(m));
    else text_m = str(m);
    if(s1) text_s = ("0" + str(s));
    else text_s = str(s);
    if(f1) text_f = ("0" + str(frameCount%60));
    else text_f = str(frameCount%60);
    TextTime();
    
    // 当たり判定
    for(int i = 0; i < 60; i++){
      sound_on_x[i] = soundBall.pos.x + ball_size/2 * cos(i*6);
      sound_on_y[i] = soundBall.pos.y + ball_size/2 * sin(i*6);
    }
    
    if(ball_num < ball_num_def) ball_now = ball_num;
    else ball_now = ball_num_def;
    
    if(mouse_release){
      for(int ball = 0; ball <= ball_now; ball++){
        for(int angle = 0; angle < 60; angle++){
          // 6°ごとの円周座標を計算
          shoot_on_x[ball][angle] = shootBall[ball].pos.x + ball_size/2 * cos(angle*6);
          shoot_on_y[ball][angle] = shootBall[ball].pos.y + ball_size/2 * sin(angle*6);
          
          boolean shoot_up_sound = shootBall[ball].pos.y > soundBall.pos.y;
          boolean shoot_left_sound = shootBall[ball].pos.x > soundBall.pos.x;
          boolean shoot_right_sound = shootBall[ball].pos.x < soundBall.pos.x;
          boolean shoot_under_sound = shootBall[ball].pos.y < soundBall.pos.y;
          
          for(int angle2 = 0; angle2 < 60; angle2++){
            if(angle < 30){
              up_left_x = shoot_on_x[ball][angle] <= sound_on_x[angle2];
              up_left_y = shoot_on_y[ball][angle] <= sound_on_y[angle2];
              under_left_x = shoot_on_x[ball][angle] <= sound_on_x[angle2];
              under_left_y = shoot_on_y[ball][angle] >= sound_on_y[angle2];
              under_right_x = shoot_on_x[ball][angle] >= sound_on_x[angle2];
              under_right_y = shoot_on_y[ball][angle] >= sound_on_y[angle2];
              up_right_x = shoot_on_x[ball][angle] >= sound_on_x[angle2];
             up_right_y = shoot_on_y[ball][angle] <= sound_on_y[angle2];
            }
            else{
              up_left_x = shoot_on_x[ball][angle] <= sound_on_x[angle2];
              up_left_y = shoot_on_y[ball][angle] <= sound_on_y[angle2];
              under_left_x = shoot_on_x[ball][angle] <= sound_on_x[angle2];
              under_left_y = shoot_on_y[ball][angle] >= sound_on_y[angle2];
              under_right_x = shoot_on_x[ball][angle] >= sound_on_x[angle2];
              under_right_y = shoot_on_y[ball][angle] >= sound_on_y[angle2];
              up_right_x = shoot_on_x[ball][angle] >= sound_on_x[angle2];
              up_right_y = shoot_on_y[ball][angle] <= sound_on_y[angle2];
            }
            if(angle >= 0 && angle <= 14){
              if(shoot_up_sound && shoot_right_sound){
                if(up_right_x && up_right_y){
                  GameOver();
                }
              }
            }
            else if(angle >= 15 && angle <= 29){
              if(shoot_right_sound && shoot_under_sound){
                if(under_right_x && under_right_y){
                  GameOver();
                }
              }
            }
            else if(angle >= 30 && angle <= 44){
              if(shoot_under_sound && shoot_left_sound){
                if(under_left_x && under_left_y){
                  GameOver();
                }
              }
            }
            else if(angle >= 45 && angle <= 59){
              if(shoot_left_sound && shoot_up_sound){
                if(up_left_x && up_left_y){
                   GameOver();
                }
              }
            }
          }
        }  
      }
    }
  
    // soundBall
    SoundBall();
    
  
  
    // shootBall
  
    // 打ち出す方向を角度から調整
    inter = map(shoot_angle, 0, 90, 0, 20);
    if(shoot_angle == 45 && shoot_angle == 90){}
    else if(shoot_angle < 45){
      if(shoot_angle < 22.5f){
        ajust = map(shoot_angle, 0, 22.5f, -0.2f, -1.1f);
        if(shoot_angle > 4 && shoot_angle < 19){
          ajust = ajust - 0.3f;
        }
        inter = inter - ajust;
      }
      else if(shoot_angle > 22.5f){
        ajust = map(shoot_angle, 22.5f, 45, -1.0f, -0.2f);
        if(shoot_angle > 27 && shoot_angle < 39){
          ajust = ajust - 0.15f;
        }
        inter = inter - ajust;
      }
    }
    else if(shoot_angle > 45){
      if(shoot_angle < 67.5f){
        ajust = map(shoot_angle, 45, 67.5f, -0.1f, 0.8f);
        inter = inter - ajust;
      }
      else if(shoot_angle > 67.5f){
        ajust = map(shoot_angle, 67.5f, 90, 0.8f, 0.05f);
        if(shoot_angle > 69 && shoot_angle < 88){
          ajust = map(shoot_angle, 69, 88, 0.8f, 0.2f);
          if(shoot_angle > 71 && shoot_angle < 73){
            ajust = ajust + 0.1f;
          }
          else if(shoot_angle >= 73 && shoot_angle <= 85.5f){
            ajust = ajust + 0.2f;
          }
          else if(shoot_angle > 85.5f && shoot_angle < 86){
            ajust = ajust + 0.1f;
          }
        }
        inter = inter - ajust;
      }
    }
    x_v[ball_num] = 20 - inter;
    y_v[ball_num] = inter;
    if(shoot_angle == 90){
      x_v[ball_num] = 0;
      y_v[ball_num] = 20;
    }
    
    // shootBallを描写
    if(mouse_release){
      if(delete){
        for(int i = 0; i < ball_num_def; i++){
          if(i == ball_count % ball_num_def){}
          else{
            shootBall[i].update(x_v[i], y_v[i], x_d[i], y_d[i], game_over);
            shootBall[i].draw();
          }
        }
      }
      else{
        if(ball_count < ball_num_def + 1){
          for(int i = 0; i < ball_num + 1; i++){
            shootBall[i].update(x_v[i], y_v[i], x_d[i], y_d[i], game_over);
            shootBall[i].draw();
          }
        }
        else{
          for(int i = 0; i < ball_num_def; i++){
            shootBall[i].update(x_v[i], y_v[i], x_d[i], y_d[i], game_over);
            shootBall[i].draw();
          }
        }
      }
    }
  
    // shootBallのエフェクト
    if(mousePressed){
      // 照準の線を描写
      stroke(color(aim_H, aim_S, aim_B));
      strokeWeight(aim_thick);
      line(300, 600, mouseX, mouseY);
      // これから撃つ球を描写
      shoot_fi_c = color(shoot_fi_H, shoot_fi_S, shoot_fi_B);
      fill(shoot_fi_c);
      noStroke();
      ellipse(300, 600, ball_size, ball_size);
    }
    // 消える球のワープエフェクト
    BallWarp();
    if(effect_fi_H == 0) effect_fi_H = 360;
    else effect_fi_H -= 5;
    if(effect_st_H == 360) effect_st_H = 0;
    else effect_st_H += 5;
    
    
  }
  else{
    GameOver();
  }
}


public void mouseReleased(){
  if(!game_start) mouse_release_start = true;
  if(!game_over && game_start && start_frame + 10 < frameCount){
    // shootBallの数の管理
    if(ball_count == 0){
      ball_count++;
    }
    else{
      if(ball_num + 1 < ball_num_def){
        ball_num++;
        ball_count++;
      }
      else{
        ball_num = 0;
        ball_count++;
      }
    }
  
    // 打ち出す角度を求める
    shoot_angle = degrees((float)Math.atan2(mouseY - 600, mouseX - 300));
    if(shoot_angle < 0){
      shoot_angle = degrees((float)Math.atan2(mouseY - 600, mouseX - 300))* -1 - 180;
      shoot_angle *= -1;
      if(shoot_angle > 90){
        shoot_angle = degrees((float)Math.atan2(mouseY - 600, mouseX - 300)) * -1;
        x_d[ball_num] = 1;
        y_d[ball_num] = -1;
      }
      else{
        x_d[ball_num] = -1;
        y_d[ball_num] = -1;
      }
    } 
    else return;
  
    // bool値を操作
    mouse_release = true;
    delete = false;
    
    // shootBallを設定
    shoot_fi_c = color(shoot_fi_H, shoot_fi_S, shoot_fi_B);
    shootBall[ball_num] = new shootBall(shoot_fi_c);
  }
}


public void mousePressed(){
  if(game_start){
    frame_count = frameCount;
    warp_out_effect_count = 0;
    warp_in_effect_count = effect_frame;
    in_effect_A = 255;
    out_effect_A = 255;
    if(ball_num_def <= ball_count){
      warp_x = shootBall[(ball_count) % ball_num_def].pos.x;
      warp_y = shootBall[(ball_count) % ball_num_def].pos.y;
      delete  = true;
    }
  }
}





public void BallWarp(){
  
  // 画面上のボールの数が、デフォルト以上になったら
  if(ball_num_def <= ball_count){
    
    if(frame_count + effect_frame >= frameCount){
      
      // エフェクトフレーム数カウントが、エフェクトを続けるフレーム数以内であれば
      if(warp_out_effect_count < effect_frame){
        warp_out_effect_count++;
        warp_in_effect_count--;
        
        // shootBallが出てくるワープを描写
        fill(effect_fi_H, effect_fi_S, effect_fi_B, out_effect_A);
        stroke(effect_st_H, effect_st_S, effect_st_B, out_effect_A);
        strokeWeight(effect_thick);
        ellipse(300, 600, warp_out_effect_count * effect_size, warp_out_effect_count * effect_size);
        
        // これから撃つ球を描写
        shoot_fi_c = color(shoot_fi_H, shoot_fi_S, shoot_fi_B);
        fill(shoot_fi_c);
        noStroke();
        ellipse(300, 600, ball_size, ball_size);
        
        if(warp_in_effect_count > 0){
          // shootBallが入るワープを描写
          fill(effect_fi_H, effect_fi_S, effect_fi_B, in_effect_A);
          stroke(effect_st_H, effect_st_S, effect_st_B, in_effect_A);
          strokeWeight(effect_thick);
          ellipse(warp_x, warp_y, warp_in_effect_count * effect_size, warp_in_effect_count * effect_size);
        }
        in_effect_A -= 255/effect_frame;
      }
    }
    else if(out_effect_A > 0){
      // shootBallが入るワープが段々消えていくエフェクト
      out_effect_A -= 255/effect_frame * 1.5f;
      fill(effect_fi_H, effect_fi_S, effect_fi_B, out_effect_A);
      stroke(effect_st_H, effect_st_S, effect_st_B, out_effect_A);
      strokeWeight(effect_thick);
      ellipse(300, 600, warp_out_effect_count * effect_size, warp_out_effect_count * effect_size);
    }
  }
}
public void GameStart(){
  
}

public void GameOver(){
  // 本関数が呼び出された始めの一回だけ行う
  if(!game_over){
    // 背景を描写
    BackGround(0);
    Block();
    
    if(ball_count < ball_num_def + 1){
      for(int i = 0; i < ball_num + 1; i++){
        stop_x[i] = shootBall[i].pos.x;
        stop_y[i] = shootBall[i].pos.y;
        shootBall[i].update(stop_x[i], stop_y[i],0,0, game_over);
        shootBall[i].draw();
      }
    }
    else{
      for(int i = 0; i < ball_num_def; i++){
        stop_x[i] = shootBall[i].pos.x;
        stop_y[i] = shootBall[i].pos.y;
        shootBall[i].update(stop_x[i], stop_y[i],0,0, game_over);
        shootBall[i].draw();
      }
    }
    
    for(int i = 0; i < explosion_num; i++){
      explosion_x[i] = soundBall.pos.x;
      explosion_y[i] = soundBall.pos.y;
      explosion_dx[i]= random(-1.5f, 1.5f);
      explosion_dy[i]= random(-1.5f, 1.5f);
    }
    
    explosion_point_x = soundBall.pos.x;
    explosion_point_y = soundBall.pos.y;
    
    game_over = true;
    explosion_count = frameCount;
    frame_count = frameCount;
    explosion_A = 255;
    game_over_A = 0;
    game_over_y = 300;
  }
  
  // 本関数の呼び出し二回目以降
  else{
    
    // soundBallが爆発している間
    if(explosion_count + 120 >= frame_count && frame_count + 1 == frameCount){
      frame_count++;
      
      BackGround(0);
      
      Block();
      
      TextTime();
      
      if(ball_count < ball_num_def + 1){
        for(int i = 0; i < ball_num + 1; i++){
          shootBall[i].update(stop_x[i], stop_y[i],0,0, game_over);
          shootBall[i].draw();
        }  
      }
      else{
       for(int i = 0; i < ball_num_def; i++){
          shootBall[i].update(stop_x[i], stop_y[i],0,0, game_over);
          shootBall[i].draw();
        }
      }
      
      for(int i = 0; i < explosion_num; i++){
        fill(sound_fi_c, explosion_A);
        noStroke();
        explosion_x[i] += explosion_dx[i];
        explosion_y[i] += explosion_dy[i];
        ellipse(explosion_x[i], explosion_y[i], 6, 6);
      }
      explosion_A = explosion_A - 255/60;
    }
    
    // Game Over 表示画面
    if(explosion_count + 120 < frame_count && frame_count + 1 == frameCount){
      
      frame_count++;
      if(game_over_A < 255){
        BackGround(-5);
        game_over_y--;
        game_over_A += 2;
        TextGameOver(game_over_A, game_over_y);
      }
      else{
        BackGround(-5);
        
        // Game Over 表示
        TextGameOver(game_over_A, game_over_y);
        
        
        // 時間表示
        fill(time_H, time_S, time_B);
        textSize(40);
        text("result :", 20, 300);
        textFont(font_time);
        textSize(40);
        if(h != 0) text(text_h +" h. "+ text_m +" m. "+ text_s +" s", 190, 300);
        else{
          if(m != 0) text(text_m +" m. "+ text_s +" s", 220, 300);
          else text(text_s +" s", 280, 300);
        }
        
        // continueボタン
        
        fill(170, 35, 90);
        textFont(font_title);
        textSize(40);
        text("Continue?", 201, 416);
        
        fill(170, 95, 90, 90);
        stroke(170, 95, 90);
        strokeWeight(4);
        strokeJoin(BEVEL);
        rect(120, 360, 480, 440);
        
        // titleボタン
        
        fill(170, 35, 90);
        textFont(font_title);
        textSize(40);
        text("Go Title?", 213, 516);
        
        fill(170, 95, 90, 90);
        stroke(170, 95, 90);
        strokeWeight(4);
        strokeJoin(BEVEL);
        rect(120, 460, 480, 540);
        
        
        
        // continueボタンの上にマウスがあれば
        boolean x = (mouseX >= 122 && mouseX <= 478);
        boolean y = (mouseY >= 362 && mouseY <= 438);
        if(x && y){
          // ボタンの色を変更
          fill(170, 95, 90, 90);
          stroke(170, 95, 90);
          strokeWeight(4);
          strokeJoin(BEVEL);
          rect(120, 360, 480, 440);
          if(mousePressed) game_continue = true;
        }
        
        // GoTitleボタンの上にマウスがあれば
        x = (mouseX >= 122 && mouseX <= 478);
        y = (mouseY >= 462 && mouseY <= 538);
        if(x && y){
          // ボタンの色を変更
          fill(170, 95, 90, 90);
          stroke(170, 95, 90);
          strokeWeight(4);
          strokeJoin(BEVEL);
          rect(120, 460, 480, 540);
          if(mousePressed) go_title = true;
        }
      }
    }
    if(game_continue){
      BackGround(0);
      
      fill(sound_fi_c, explosion_A);
      noStroke();
      if(continue_count < 121){
        for(int i = 0; i < explosion_num; i++){
          explosion_x[i] = explosion_x[i] - explosion_dx[i];
          explosion_y[i] = explosion_y[i] - explosion_dy[i];
          ellipse(explosion_x[i], explosion_y[i], 6, 6);
        }
        explosion_A = explosion_A + 255/60;
      }
      else if(continue_count < 151) ellipse(explosion_point_x, explosion_point_y, ball_size, ball_size);
      else{
        ellipse(explosion_point_x, explosion_point_y, ball_size, ball_size);
        explosion_point_y -= 40;
      }
      
      // Let's Go! 表示
      fill(lets_go_H, lets_go_S, lets_go_B);
      textFont(font_text);
      text("Let's GO!", 100, 285); 
      
      // ボタンの四角形
      fill(170, 30, 100, 50);
      stroke(170, 95, 90);
      strokeWeight(4);
      strokeJoin(BEVEL);
      rect(120, 360, 480, 440); 
      
      // ボタンの中の文字
      fill(170, 50, 100);
      textFont(font_title);
      textSize(40);
      text("Continue!", 200, 416);
      
      text_h = "00";
      text_m = "00"; 
      text_s = "00";
      text_f = "00";
      
      block_num = 1;
      Block();
      TextTime();
      
      continue_count++;
      
      if(continue_count >= 200 && frameCount%60 == 0){
        GameReset();
        ball_num = 0;
        return;
      }
    }
    if(go_title){
      BackGround(0); 
      
      ball_num = 1;
      
      // ボタンの四角形
      fill(170, 95, 90, 180);
      stroke(170, 95, 90);
      strokeWeight(4);
      strokeJoin(BEVEL);
      rect(120, 460, 480, 540); 
      
      // ボタンの中の文字
      fill(170, 50, 100);
      textFont(font_title);
      textSize(37);
      text("Thanks for Playing!", 127, 514);
      
      continue_count++;
      
      if(continue_count >= 30){
        GameReset();
        ball_num = 5;
        game_start = false;
        return;
      }
    }
  }
  game_over = true;
}

public void BackGround(int B){
  background(back_H, back_S+B, back_B+B);
  for(int i = 0; i < 30; i++){
    stroke(back_line_H, back_line_S+B, back_line_B+B);
    strokeWeight(1);
    line(0, i * 20, width, i * 20);
    line(i * 20, 0, i * 20, height);
  }
}

public void Block(){
  for(int i = 0; i < block_num; i++){
    if(i == 1) block_fi_A = 0;
    else block_fi_A = 40;
    fill(color(block_fi_H, block_fi_S, block_fi_B, block_fi_A));
    stroke(color(block_st_H, block_st_S, block_st_B));
    strokeWeight(block_st_thick);
    rect(left_upper_x[i], left_upper_y[i], right_under_x[i], right_under_y[i]);
  }
}

public void TextTime(){
  fill(time_H, time_S, time_B, 255);
  textFont(font_time);
  textSize(40);
  text(text_h +":"+ text_m +":"+ text_s +":"+ text_f, 67, 94);
}

public void TextGameOver(int A, int y){
  fill(game_over_H, game_over_S, game_over_B, A);
  textFont(font_text);
  text("GAME OVER", 13, y);
}

public void GameReset(){
  
  start_frame = 0;
 
  soundBall = new soundBall(random(1, 600), 0, sound_fi_c);
  
  ball_count = 0;
  block_num = block_num_def;
  mouse_release = false;
  
  text_h = "00"; text_m = "00"; text_s = "00"; text_f = "00";
  
  game_continue = false;
  go_title = false;
  continue_count = 0;
  game_over = false;
  
  voiceing_x = -600;
  shooting_y = -50;
  
  s = 0;
  m = 0;
  h = 0;
}

public void SoundBall(){
  // 声を変換
  float max_Band = 0.0f;
  fft.forward(in.mix);
  for(int i = 0; i < fft.specSize(); i++){
    if(max_Band < fft.getBand(i)){
      max_Band = fft.getBand(i);
    }
  }
  float sound_velocity = max_Band * sound_ajust;
  
  fill(sound_fi_H, sound_fi_S, sound_fi_B);

  soundBall.update(sound_velocity);
  soundBall.draw();
}
class shootBall{
  
  float velocity_x;
  float velocity_y;
  
  PVector pos; // position  座標
  PVector dir; // direction 反転
  int   c1;  // color     球の色
  float U_x; // Upper_x
  float U_y;
  float B_x;
  float B_y;
  float pre_x;
  float pre_y;
  boolean side_flag = false;
  boolean flat_flag = false;
  
  float inter_x;
  float inter_y;
  

  shootBall(int Fc){
    pos = new PVector(300, 600);
    dir = new PVector(1, 1);
    c1 = Fc;
  }
  
  public void update(float x_v, float y_v, int x_d, int y_d, boolean go){
    if(!go){
      ScreenReflect();
      
      for(int i = 0; i < block_num; i++){
        if(i == 1) continue;
        BlockJudge(i);
        boolean flat = (U_x <= pos.x && pos.x <= B_x);
        boolean side = (U_y <= pos.y && pos.y <= B_y);
        boolean side_flag = (side && !(U_x <= pre_x && pre_x <= B_x));
        boolean flat_flag = (flat && !(U_y <= pre_y && pre_y <= B_y));
        boolean flat_top = (U_y <= pos.y);
        boolean flat_buttom = (B_y >= pos.y);
        boolean side_l = (U_x <= pos.x);
        boolean side_r = (B_x >= pos.x);
        if(flat && (flat_top && flat_buttom) && flat_flag){
          pos.y = pre_y;
          dir.y *= -1;
          flat_flag = false;
        }
        else if(side && (side_r && side_l) && side_flag){
          pos.x = pre_x;
          dir.x *= -1;
          side_flag = false;
        }
      }
      
      pre_x = pos.x;
      pre_y = pos.y;
      fill(c1);
      noStroke();
      
      pos.add(x_v * dir.x*x_d, y_v * dir.y*y_d);
    }
    else{
      fill(c1);
      noStroke();
      pos.x = x_v;
      pos.y = y_v;
    }
  }
  
  public void draw(){
    ellipse(pos.x, pos.y, ball_size, ball_size);
  }

  
  // 画面反射
  public void ScreenReflect(){
    if(pos.x >= width){
      pos.x = width;
      dir.x *= -1;
    }
    if(pos.x <= 0){
      pos.x = 0;
      dir.x *= -1;
    }
    if(pos.y >= height){
      pos.y = height;
      dir.y *= -1;
    }
    if(pos.y <= 0){
      pos.y = 0;
      dir.y *= -1;
    }
  }
  
  // ブロック判定整理
  public void BlockJudge(int i){
    if(left_upper_x[i] < right_under_x[i]){
      U_x = left_upper_x[i];
      B_x = right_under_x[i];
    }
    else{
      U_x = right_under_x[i];
      B_x = left_upper_x[i];
    }
    if(left_upper_y[i] < right_under_y[i]){
      U_y = left_upper_y[i];
      B_y = right_under_y[i];
    }
    else{
      U_y = right_under_y[i];
      B_y = left_upper_y[i];
    }
  }
}
class soundBall{
  PVector pos; //
  PVector dir; //
  int   c1; //
  
  float U_x; // Upper_x
  float U_y;
  float B_x;
  float B_y;
  
  float pre_x;
  float pre_y;
  
  boolean side_flag = false;
  boolean flat_flag = false;
  
  soundBall(float px, float py, int Fc){
    pos = new PVector(px, py);
    dir = new PVector(-1, 1);
    c1 = Fc;
  }
  
  public void update(float velocity){
    
    ScreenReflect();
    
    for(int i = 0; i < block_num; i++){
      
      BlockJudge(i);
      
      boolean flat = (U_x <= pos.x && pos.x <= B_x);
      boolean side = (U_y <= pos.y && pos.y <= B_y);
      
      boolean side_flag = (side && !(U_x <= pre_x && pre_x <= B_x));
      boolean flat_flag = (flat && !(U_y <= pre_y && pre_y <= B_y));
      
      boolean flat_top = (U_y <= pos.y);
      boolean flat_buttom = (B_y >= pos.y);
      boolean side_l = (U_x <= pos.x);
      boolean side_r = (B_x >= pos.x);
      
      
      if(flat && (flat_top && flat_buttom) && flat_flag){
        pos.y = pre_y;
        dir.y *= -1;
        flat_flag = false;
      }
      else if(side && (side_r && side_l) && side_flag){
        pos.x = pre_x;
        dir.x *= -1;
        side_flag = false;
      }
    }
    pre_x = pos.x;
    pre_y = pos.y;
    
    fill(c1);
    noStroke();
    pos.add(velocity * dir.x, velocity * dir.y);
  }
  
  public void draw(){
    ellipse(pos.x, pos.y, ball_size, ball_size);
  }
  
  
  
  // 画面反射
  public void ScreenReflect(){
    if(pos.x >= width){
      pos.x = width;
      dir.x *= -1;
    }
    if(pos.x <= 0){
      pos.x = 0;
      dir.x *= -1;
    }
    if(pos.y >= height){
      pos.y = height;
      dir.y *= -1;
    }
    if(pos.y <= 0){
      pos.y = 0;
      dir.y *= -1;
    }
  }
  
  // ブロック判定整理
  public void BlockJudge(int i){
    if(left_upper_x[i] < right_under_x[i]){
      U_x = left_upper_x[i];
      B_x = right_under_x[i];
    }
    else{
      U_x = right_under_x[i];
      B_x = left_upper_x[i];
    }
    if(left_upper_y[i] < right_under_y[i]){
      U_y = left_upper_y[i];
      B_y = right_under_y[i];
    }
    else{
      U_y = right_under_y[i];
      B_y = left_upper_y[i];
    }
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ShootING_VoiceING" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
