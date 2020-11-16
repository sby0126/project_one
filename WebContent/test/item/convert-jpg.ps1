[Reflection.Assembly]::LoadWithPartialName('System.Drawing')

function ConvertTo-Jpg
{
    [cmdletbinding()]
    param([Parameter(Mandatory=$true, ValueFromPipeline = $true)] $Path)

    process{
        if ($Path -is [string])
        { $Path = get-childitem $Path }

        $Path | foreach {
            $image = [System.Drawing.Image]::FromFile($($_.FullName));
            $FilePath = [IO.Path]::ChangeExtension($_.FullName, '.jpg');
            $image.Save($FilePath, [System.Drawing.Imaging.ImageFormat]::Jpeg);
            $image.Dispose();
        }
    }

 }

 #Run ConvertTo-Jpg function
 Get-ChildItem *.gif | ConvertTo-Jpg