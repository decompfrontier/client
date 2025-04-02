use anyhow::{bail, Result};
use lazy_static::lazy_static;
use std::path::PathBuf;

#[derive(serde::Deserialize)]
pub struct Config {
    pub build_target: String,
    pub functions_csv: String,
    pub default_version: Option<String>,
    pub decomp_me: Option<ConfigDecompMe>,
	
	// bf
	pub original_elf: String,
	pub base_address: u64,
	pub data_symbols: String,
	pub tools_path: String,
	pub data_path: String,
	pub build_path: String,
}

#[derive(serde::Deserialize)]
pub struct ConfigDecompMe {
    pub compiler_name: String,

    /// Compilation flags that are used for creating scratches.
    /// Can be overridden using a compilation database.
    pub default_compile_flags: String,
}

lazy_static! {
    static ref CONFIG: Config = {
        let toml_path = get_repo_root()
            .expect("failed to get repo root")
			// todo: make this customizable based from the version
            .join("replika/config.toml");
        let raw = std::fs::read_to_string(toml_path.as_path()).expect("failed to read config file");
        toml::from_str(&raw).expect("failed to parse config file")
    };
}

pub fn get_config() -> &'static Config {
    &CONFIG
}

pub fn get_repo_root() -> Result<PathBuf> {
    let current_dir = std::env::current_dir()?;
    let mut dir = current_dir.as_path();

    loop {
		// todo: move this to a config
        if ["replika", "src"].iter().all(|name| dir.join(name).is_dir()) {
            return Ok(dir.to_path_buf());
        }

        match dir.parent() {
            None => {
                bail!("failed to find repo root -- run this program inside the repo");
            }
            Some(parent) => dir = parent,
        };
    }
}

pub fn get_tools_path() -> Result<PathBuf> {
	let name = get_config().tools_path.clone();
    Ok(get_repo_root()?.join(name))
}

fn get_version_specific_dir_path(dir_name: &str, version: Option<&str>) -> Result<PathBuf> {
    let dir_name = if let Some(v) = version {
        format!("{}/{}", dir_name, v)
    } else {
        dir_name.to_string()
    };

    Ok(get_repo_root()?.join(dir_name))
}

pub fn get_data_path(version: Option<&str>) -> Result<PathBuf> {
	let name = get_config().data_path.clone();
    get_version_specific_dir_path(&name, version)
}

pub fn get_build_path(version: Option<&str>) -> Result<PathBuf> {
	let build = get_config().build_path.clone();
    get_version_specific_dir_path(&build, version)
}

pub fn get_data_symbol_csv_path(version: Option<&str>) -> Result<PathBuf> {
	let sym = get_config().data_symbols.clone();
	Ok(get_data_path(version)?.join(sym))
}
