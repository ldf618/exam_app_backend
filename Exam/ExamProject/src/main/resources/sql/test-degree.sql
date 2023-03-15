/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  ldiez
 * Created: 4 feb. 2023
 */

begin;
truncate degrees cascade;
insert into degrees (id, name) values (1, 'Ingenieria Informática');
insert into degrees (id, name) values (2, 'Quimicas');
insert into degrees (id, name) values (3, 'Física');
insert into degrees (id, name) values (4, 'ADE');
insert into degrees (id, name) values (5, 'Bioinformática');
insert into degrees (id, name) values (6, 'Derecho');
insert into degrees (id, name) values (7, 'Ingenieria Industrial');
insert into degrees (id, name) values (8, 'Marketing');
insert into degrees (id, name) values (9, 'Informática de Gestión');
insert into degrees (id, name) values (10, 'Informática de Sistemas');
commit;