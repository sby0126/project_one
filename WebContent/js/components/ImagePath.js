
const config = {
    // 트래픽 월 100GB
    isLocalMode: true,
    githubCDN: "https://raw.githubusercontent.com/biud436/project_one/main/WebContent",
};

class ImagePath {   

    static getParentPath() {
        if(config.isLocalMode) {
            return "";
        } else {
            return config.githubCDN;
        }
    }

    static getPath(pageType, genderType, shopType, filename) {
        switch(pageType) {
            default:
            case "shop":
                this.getShopPath(genderType, shopType, filename);
                break;
            case "item":
                this.getItemPath(genderType, shopType, filename);
                break;
            case "sale":
                this.getSalePath(genderType, shopType, filename);
                break;
        }
    }

    static getShopPath(genderType, shopType, filename) {
        return this.getParentPath() + `/images/shop/${genderType}/${shopType}/${filename}`;
    }

    static getItemPath(genderType, shopType, filename) {
        return this.getParentPath() + `/images/item/${genderType}/${shopType}/${filename}`;
    }

    static getSalePath(genderType, filename) {
        return this.getParentPath() + `/images/sale/${genderType}/${filename}`;
    }
}

export {ImagePath};