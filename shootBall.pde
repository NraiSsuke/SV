class shootBall{
  
  float velocity_x;
  float velocity_y;
  
  PVector pos; // position  座標
  PVector dir; // direction 反転
  color   c1;  // color     球の色
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
  

  shootBall(color Fc){
    pos = new PVector(300, 600);
    dir = new PVector(1, 1);
    c1 = Fc;
  }
  
  void update(float x_v, float y_v, int x_d, int y_d, boolean go){
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
  
  void draw(){
    ellipse(pos.x, pos.y, ball_size, ball_size);
  }

  
  // 画面反射
  void ScreenReflect(){
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
  void BlockJudge(int i){
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
