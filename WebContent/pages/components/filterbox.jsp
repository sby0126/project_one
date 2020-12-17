<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="header-filter-box">
                        <!-- 검색 필터 래퍼 -->
                        <div class="header-filter-box-wrapper">
                            <!-- 검색 필터 하단 : 대/중/소 분류 -->
                            <div class="header-filter-box-footer">
                                <!-- 검색 필터 왼쪽 : 카테고리 선택 -->
                                <div class="header-filter-box-footer-left">
                                    <div class="header-filter-box-footer-left-button">
                                        <span>상품 카테고리<em>전체(0)</em></span>
                                        <i class="fa fa-caret-down"></i>
                                    </div>
                                    <div class="header-filter-box-left-dropdown-menu" data-attr="상품 카테고리">
                                        <div class="header-filter-box-left-dropdown-menu-content">
                                            <dl>
                                                <dt>샵 카테고리</dt>
                                                <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>ALL</span></label></dd>
                                                <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>OUTER</span></label></dd>
                                                <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>TOP</span></label></dd>
                                                <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>BOTTOM</span></label></dd>
                                                <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>BAG</span></label></dd>
                                                <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>SHOES</span></label></dd>
                                                <dd><label><input class="header-filter-box-left-shop-categories" type="checkbox" name="categories"><span>ACC·ETC</span></label></dd>
                                            </dl>
                                        </div>                                
                                    </div>
                                </div>
                                <!-- 검색 필터 중앙 : 검색 -->
                                <div class="header-filter-box-footer-center">
                                    <label for="name-search"></label><input class="input-non-border-box" type="text" placeholder="이름으로 검색">
                                </div>
                                <!-- 검색 필터 오른쪽 : 분류 -->
                                <div class="header-filter-box-footer-right">
                                    <ul>
                                        <li></li>
                                        <li>
                                            <div id="slide-bar">

                                            </div>
                                        </li>
                                        <li></li>
                                        <li></li>
                                    </ul>                         
                                </div>
                            </div>
                        </div>
                    </div>