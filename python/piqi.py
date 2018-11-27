clc;
close all;
clear all;
A = imread('20180604112353.png');
B = rgb2gray(A);
B = imresize(B,[107,80]);
% thresh = graythresh(A);
% C = im2bw(A,thresh);
% imtool(B);
ft = fopen('comment.c','w+');
[m,n] = size (B);
C = uint8(ones(m,n)*255);
for i =1:1:m
    fprintf(ft,'\n');
    for j = 1:1:n
%         fprintf('%d',B(i,j));
        if B(i,j)>235
            C(i,j) = 255;
            fprintf(ft,'%s','  ');
        elseif B(i,j) <= 235 && B(i,j) > 205
            C(i,j) = 210;
            fprintf(ft,'%s','~.~');
        elseif B(i,j) <= 205 && B(i,j) > 150
            C(i,j) = 180;
            fprintf(ft,'%s','%!%');
        elseif B(i,j) <= 150 && B(i,j) >90
            C(i,j) = 120;
            fprintf(ft,'%s','@,@');
        else
            C(i,j) = 0;
            fprintf(ft,'%s','#`#');
        end
    end
end
% imtool(C);
fclose(ft);
fprintf('%s','That is all!');