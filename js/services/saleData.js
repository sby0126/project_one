const saleData = [{"category":"sale","title":"인기슈즈 59% SALE","shop":"힙합퍼","term":"11.05 ~ 11.30","url":"1604551820253_m.jpg"},{"category":"sale","title":"올세인츠 up to 50% off!","shop":"SI빌리지","term":"11.05 ~ 11.16","url":"1604551682098_m.jpg"},{"category":"sale","title":"스포츠브랜드 슈즈 세일 50%","shop":"ABC마트","term":"11.05 ~ 11.15","url":"1604551154677_m.jpg"},{"category":"sale","title":"에잇세컨즈 신상특가 ~50%","shop":"SSF샵","term":"11.03 ~ 재고 소진시","url":"1604370283744_m.jpg"},{"category":"sale","title":"어반에이지 20FW 빅세일 68%","shop":"어반에이지","term":"11.03 ~ 재고 소진시","url":"1604369757518_m.jpg"},{"category":"sale","title":"명품하이엔드 ~52%할인","shop":"셀렉온","term":"11.03 ~ 11.09","url":"1604369231024_m.jpg"},{"category":"sale","title":"탑텐 아우터페스티벌 50%off","shop":"탑텐","term":"10.30 ~ 11.30","url":"1604034563430_m.jpg"},{"category":"sale","title":"네스티킥x네스티팬시클럽 80%","shop":"크루비","term":"10.30 ~ 재고 소진시","url":"1604034171416_m.jpg"},{"category":"sale","title":"플루크 미드세일 50%!","shop":"인플럭스","term":"10.30 ~ 11.10","url":"1604033872625_m.jpg"},{"category":"sale","title":"챔피온 겨울상품 ~30%off","shop":"29CM","term":"10.30 ~ 11.11","url":"1604033519539_m.jpg"},{"category":"sale","title":"슈즈브랜드 세일 up to 70%","shop":"ABC마트","term":"10.30 ~ 11.01","url":"1604033228818_m.jpg"},{"category":"sale","title":"필루미네이트 빅세일 -70%","shop":"필루미네이트","term":"10.30 ~ 재고 소진시","url":"1604030222317_m.jpg"},{"category":"sale","title":"베이직하우스 미드세일 40%off","shop":"베이직하우스","term":"10.26 ~ 재고 소진시","url":"1603637206984_m.jpg"},{"category":"sale","title":"브랜드 니트컬렉션 ~77%","shop":"무신사","term":"10.26 ~ 11.08","url":"1603636719854_m.jpg"},{"category":"sale","title":"컨버스 모음전 40% SALE","shop":"힙합퍼","term":"10.22 ~ 10.26","url":"1603335324081_m.jpg"},{"category":"sale","title":"명품브랜드 아우터 ~66% off","shop":"셀렉온","term":"10.22 ~ 10.26","url":"1603334465572_m.jpg"},{"category":"sale","title":"인기명품 특가 up to 57%","shop":"SSF샵","term":"10.22 ~ 재고 소진시","url":"1603333551834_m.jpg"},{"category":"sale","title":"가을모자 특가모음 ~68%","shop":"슈마커","term":"10.20 ~ 10.24","url":"1603112686912_m.jpg"},{"category":"sale","title":"인기브랜드 상품 최대 ~86%","shop":"무신사","term":"10.15 ~ 10.19","url":"1602745468255_m.jpg"},{"category":"sale","title":"브랜드 니트특가 최대 62%할인","shop":"크루비","term":"10.12 ~ 재고 소진시","url":"1602490555500_m.jpg"},{"category":"sale","title":"아디다스 인기상품 50%off","shop":"무신사","term":"10.12 ~ 11.01","url":"1602489498501_m.jpg"},{"category":"sale","title":"에르노 최대 ~50%off","shop":"SI빌리지","term":"10.08 ~ 10.19","url":"1602076811370_m.jpg"},{"category":"sale","title":"컨템포러리 시계 ~65% 할인","shop":"셀렉온","term":"10.08 ~ 10.12","url":"1602074775870_m.jpg"},{"category":"sale","title":"스포츠 슈즈 가을맞이 ~80%","shop":"풋스케이프","term":"10.08 ~ 10.31","url":"1602074396229_m.jpg"},{"category":"sale","title":"인기브랜드 아우터 ~26%off","shop":"힙합퍼","term":"10.05 ~ 10.31","url":"1601865189187_m.jpg"},{"category":"sale","title":"플라이데이 시즌오프 ~70%","shop":"플라이데이","term":"09.30 ~ 재고 소진시","url":"1601381875086_m.jpg"},{"category":"sale","title":"칼하트 최대 67%할인!","shop":"에이랜드","term":"09.30 ~ 10.04","url":"1601381428417_m.jpg"},{"category":"sale","title":"나이키 추석세일 ~50%","shop":"ABC마트","term":"09.30 ~ 10.07","url":"1601380311236_m.jpg"},{"category":"sale","title":"8seconds 추석맞이 up to 50%","shop":"에잇세컨즈","term":"09.29 ~ 10.11","url":"1601351559719_m.jpg"},{"category":"sale","title":"자라 해피추석 40%off","shop":"자라","term":"09.29 ~ 재고 소진시","url":"1601351076269_m.jpg"}];

const saleImg = {
	'1604551154677_m.jpg': '12MeWFqRzRMYT0CUHzDs6FTBPeWfax6V8',
'1604551820253_m.jpg': '13Q_eO3OUZcUzbeezdANf4WDsBdY_qU3j',
'1603335324081_m.jpg': '13q7ixBjrr57CKPncmeAZ8yUMLwYxg-7M',
'1602074396229_m.jpg': '16-oBHuiIhC442FYejkj6e258u30kyUfa',
'1601381428417_m.jpg': '17tCsvZMd9z6do-RhiSi6XBnwOC7scJfG',
'1601351076269_m.jpg': '19nQinLOv6qPRpJenEO8A1B2Q9cW_puyw',
'1602076811370_m.jpg': '1AHyaWm2YJkE60tCuwGDN6CIm23BtPD4y',
'1602074775870_m.jpg': '1AkyORj8AWM28UJL_Y7LeM42vKcd-vSxk',
'1604369231024_m.jpg': '1E96GAHG0wPeMco_OD0SDs3IrcqKrnA6n',
'1601381875086_m.jpg': '1EwiEaxZHmIhJbwONIz-Meq4J6TQy5BNq',
'1604030222317_m.jpg': '1F_-R9FWVVuippw4pJ-7W77-5v5WGxZo-',
'1602489498501_m.jpg': '1KPB468MkFTdC9KoM9iki-ZPybpDl1Hrx',
'1604369757518_m.jpg': '1K_U34RSabkPHX9ntb-7AdSlJplJQgfNh',
'1603334465572_m.jpg': '1M5UCNIo2GQ0HGpvELwSJgxEVd_XgVVxW',
'1604033872625_m.jpg': '1M7YB36SWyQk7kwe3uQ9eYG_IQJCsd3ZC',
'1603112686912_m.jpg': '1NQP2JQcSRJ0XTZGC0rqrUfxwiacR-HLc',
'1602490555500_m.jpg': '1OTR9kNn7YVf22Gl5VtIJgkmnKTNpTCJz',
'1602745468255_m.jpg': '1TcXmNz2vuWq81RZvZ6QMG70NI7Al_Y2g',
'1604034563430_m.jpg': '1YdMghj8EAowf2K_iElgHwO94wU6bDJdb',
'1604370283744_m.jpg': '1Yt_6PxSRj1PxVqYhWT-awGVWslvCVXOO',
'1604033519539_m.jpg': '1ZIsEJfO97ORxpks3MegoKdHNgya3edNW',
'1604033228818_m.jpg': '1_8Q-LkBG-VelFYpCfT3jkFA_aMLu9jFb',
'1601380311236_m.jpg': '1einOcwjDyxSSNUZ1Y-SKhAJthHBhImJP',
'1604034171416_m.jpg': '1ev3UdKigeVsbY4y1e9Yq0N-IH4nxcIWS',
'1603637206984_m.jpg': '1fCwZAVvG2vltBYLuuPBp4A29enjGSVC9',
'1603333551834_m.jpg': '1go-bbiS6cw1l7ICi0r6W8jTfCnS3s3kB',
'1601865189187_m.jpg': '1koD7goUZztGVsgUDm5lFVLkJD6M1SkIg',
'1603636719854_m.jpg': '1l0D4UWvp-hp62dEG0D3b4U9uWXTZp1rO',
'1604551682098_m.jpg': '1ttBUNsgR2M_Q8GVOx6gC6F1j-og_1lJB',
'1601351559719_m.jpg': '1zAUg3T_fCL5yJhXYBBfDMWEwnPFKYbeL',
};

const imgSrc = "https://drive.google.com/uc?export=view&id=";

export {saleData, imgSrc, saleImg};