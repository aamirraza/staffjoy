# -*- mode: ruby -*-
# vi: set ft=ruby :

ip = '192.168.33.12'
cpus = 2
memory = 1024 * 6

def fail_with_message(msg)
  fail Vagrant::Errors::VagrantError.new, msg
end

Vagrant.configure(2) do |config|
  config.vm.box = "launchquickly/focal-desktop64"
  #config.vm.box = "ubuntu/bionic64"
  config.vm.network :private_network, ip: ip, hostsupdater: 'skip'
  config.vm.hostname = 'knewton-planner'

  config.vm.synced_folder ".", "/vagrant", disabled: true
  config.vm.synced_folder ".", "/home/vagrant/planner", SharedFoldersEnableSymlinksCreate: true, owner: "vagrant", group: "vagrant"

  # Install Docker
  # require plugin https://github.com/leighmcculloch/vagrant-docker-compose
  config.vagrant.plugins = "vagrant-docker-compose"

  # install docker and docker-compose
  config.vm.provision :docker
  config.vm.provision :docker_compose
  # increase original disk size
  if Vagrant.has_plugin? 'vagrant-disksize'
    config.disksize.size = '30GB' # vagrant plugin install vagrant-disksize
  else
    fail_with_message "vagrant-disksize missing, please install the plugin with this command:\nvagrant plugin install vagrant-disksize"
  end

  config.vm.provider 'virtualbox' do |vb|
    vb.gui = true
    vb.name = config.vm.hostname
    vb.customize ['modifyvm', :id, '--cpus', cpus]
    vb.customize ['modifyvm', :id, '--memory', memory]

    # Fix for slow external network connections
    vb.customize ['modifyvm', :id, '--natdnshostresolver1', 'on']
    vb.customize ['modifyvm', :id, '--natdnsproxy1', 'on']
  end

  # configure hostnames to access from localmachine
  if Vagrant.has_plugin? 'vagrant-hostmanager'
    config.hostmanager.enabled = true
    config.hostmanager.manage_host = true
    config.hostmanager.aliases = [
      'account.planner-v2.local',
      'app.planner-v2.local',
      'company.planner-v2.local',
      'faraday.planner-v2.local',
      'kubernetes.planner-v2.local',
      'myaccount.planner-v2.local',
      'superpowers.planner-v2.local',
      'signal.planner-v2.local',
      'waitlist.planner-v2.local',
      'whoami.planner-v2.local',
      'www.planner-v2.local',
      'ical.planner-v2.local',
    ]
  else
    fail_with_message "vagrant-hostmanager missing, please install the plugin with this command:\nvagrant plugin install vagrant-hostmanager"
  end
end